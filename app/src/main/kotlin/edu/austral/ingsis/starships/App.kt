package edu.austral.ingsis.starships

import edu.austral.ingsis.starships.ui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.event.EventHandler
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.HBox
import javafx.scene.layout.StackPane
import javafx.scene.layout.VBox
import javafx.stage.Stage
import starships.adapter.UIAdapter
import starships.config.Constants.*
import starships.state.GameState


private val imageResolver = CachedImageResolver(DefaultImageResolver())
private val facade = ElementsViewFacade(imageResolver)
private val keyTracker = KeyTracker()


fun main() {
    launch(MyStarships::class.java)
}
class MyStarships() : Application() {

    private var gameScene = Scene(StackPane())
    private var stateManager = StateManager(GameState())
    override fun start(primaryStage: Stage) {
        setWindowSize(primaryStage)
        cleanFacade()
        val adapter = UIAdapter()
        val inserter = EntityInSceneManager(stateManager, facade, adapter)
        facade.view.id = "facade"
        gameScene.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        gameScene = Scene(facade.view)
        addListeners(inserter, adapter)

        setStartingScene(primaryStage, adapter)

        startGame(primaryStage)
    }

    private fun cleanFacade() {
        facade.showCollider.set(false)
        facade.showGrid.set(false)
    }

    private fun setWindowSize(window: Stage) {
        window.width = GAME_WIDTH + 40
        window.height = GAME_HEIGHT + 40
    }

    private fun setStartingScene(primaryStage: Stage, adapter: UIAdapter) {
        primaryStage.scene = generateStartScene(adapter, primaryStage)
    }

    private fun generateStartScene(adapter: UIAdapter, window: Stage): Scene {
        val gameTitleLabel = Label("BloonShip")
        val onePlayerButton = generatePlayerButton(1, "One Player", adapter, window)
        val twoPlayersButton = generatePlayerButton(2, "Two Player", adapter, window)
        val threePlayersButton = generatePlayerButton(3, "Three Player", adapter, window)
        val fourPlayersButton = generatePlayerButton(4, "Four Player", adapter, window)

        val buttonLayout = HBox()
        buttonLayout.children.addAll(onePlayerButton, twoPlayersButton, threePlayersButton, fourPlayersButton)

        val completeLayout = VBox()
        completeLayout.children.addAll(gameTitleLabel, buttonLayout)

        return Scene(completeLayout)
    }

    private fun generatePlayerButton(playerQuantity: Int, text: String, adapter: UIAdapter, window: Stage): Button {
        val button =  Button(text)
        button.onAction = EventHandler {
            stateManager.setState(adapter.initializeGame(playerQuantity))
            window.scene = gameScene
        }
        return button
    }



    private fun addListeners(inserter: EntityInSceneManager, adapter: UIAdapter) : Pauser{
        val timeListener = TimeListener(stateManager, inserter, adapter)
        val pauseManager = Pauser(timeListener)
        keyTracker.scene = gameScene
        facade.timeListenable.addEventListener(timeListener)
        facade.collisionsListenable.addEventListener(CollisionListener(stateManager, inserter, adapter))
        facade.outOfBoundsListenable.addEventListener(OutOfBoundsListener(stateManager, inserter, adapter))
        keyTracker.keyPressedListenable.addEventListener(KeyPressedListener(stateManager, pauseManager, inserter, adapter))
        keyTracker.keyReleasedListenable.addEventListener(KeyReleasedListener(stateManager, inserter, adapter))
        return pauseManager
    }

    private fun startGame(primaryStage: Stage) {
        facade.start()
        keyTracker.start()
        primaryStage.show()
    }
}
class EntityInSceneManager(
    private val stateManager: StateManager,
    private val facade: ElementsViewFacade,
    private val adapter: UIAdapter
){

    fun updateFacade(newGameState: GameState){
        val map = newGameState.collideableMap.acualCollideablesMap
        val oldMap = stateManager.getState().collideableMap.acualCollideablesMap
        for (key in map.keys){
            insert(adapter.transformResultToElementModel(map.get(key)))
        }
        for (key in oldMap.keys){
            if (!map.containsKey(key)) removeById(key)
        }
    }

    private fun insert(entity: ElementModel){
        facade.elements[entity.id] = entity
    }

    fun removeById(entityId: String){
        facade.elements.remove(entityId)
    }
}

class StateManager(private var gameState: GameState){

    fun getState() : GameState{
        return gameState
    }

    fun setState(newgameState: GameState){
        gameState = newgameState
    }
}

class Pauser(private val timeListener: TimeListener){

    private var isPaused = false

    fun activatedKey(){
        if (isPaused) unPause()
        else pause()
    }
    private fun pause() {
        isPaused = true
        timeListener.activatedPause()
    }
    private fun unPause() {
        isPaused = false
        timeListener.deactivatePause()
    }
}


class TimeListener(var stateManager: StateManager, private val inserter: EntityInSceneManager, private val adapter: UIAdapter) : EventListener<TimePassed> {

    private var isPaused = false

    fun activatedPause(){
        isPaused = true
    }
    fun deactivatePause(){
        isPaused = false
    }
    override fun handle(event: TimePassed) {
        if (isPaused) return
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.updateFacade(gameState)
        stateManager.setState(gameState)
    }
}

class CollisionListener(
    private var stateManager: StateManager,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<Collision> {
    override fun handle(event: Collision) {
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.updateFacade(gameState)
        stateManager.setState(gameState)
    }
}

class KeyPressedListener(
    private var stateManager: StateManager,
    private val pauser: Pauser,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<KeyPressed> {

    override fun handle(event: KeyPressed) {
        if (event.key == KeyCode.valueOf(PAUSE_GAME)) {
            pauser.activatedKey()
        }
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.updateFacade(gameState)
        stateManager.setState(gameState)
    }
}

class KeyReleasedListener(
    private var stateManager: StateManager,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<KeyReleased> {
    override fun handle(event: KeyReleased) {
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.updateFacade(gameState)
        stateManager.setState(gameState)
    }
}

class OutOfBoundsListener(
    private var stateManager: StateManager,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<OutOfBounds> {
    override fun handle(event: OutOfBounds) {
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.removeById(event.id)
        stateManager.setState(gameState)
    }
}