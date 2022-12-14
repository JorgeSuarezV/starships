package edu.austral.ingsis.starships

import edu.austral.ingsis.starships.ui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
import javafx.scene.input.KeyCode
import javafx.scene.layout.*
import javafx.scene.paint.Color
import javafx.scene.text.Font
import javafx.stage.Stage
import starships.adapter.UIAdapter
import starships.collision.Collideable
import starships.config.Constants.*
import starships.state.GameState
import starships.state.NormalGameState
import starships.state.PausedGameState


private val imageResolver = CachedImageResolver(DefaultImageResolver())
private val facade = ElementsViewFacade(imageResolver)
private val keyTracker = KeyTracker()
private val stateManager = StateManager(PausedGameState(NormalGameState(1)))



fun main() {
    launch(MyStarships::class.java)
}
class MyStarships() : Application() {

    private var gameScene = Scene(StackPane())

    override fun start(primaryStage: Stage) {
        setWindowSize(primaryStage)
        cleanFacade()
        val adapter = UIAdapter()
        val inserter = EntityInSceneManager(stateManager, facade, adapter)

        setStartingScene(primaryStage, adapter, inserter)
        val life1 = Label("")
        val life2 = Label("")
        val score1 = Label("")
        val score2 = Label("")
        customizeLabels(life1, life2, score1,score2)
        gameScene = Scene(buildGeneralPane(life1, life2, score1, score2))
        val timeListener = TimeListener(stateManager, inserter, adapter, life1, life2, score1, score2)
        addListeners(inserter, adapter, timeListener)
        startGame(primaryStage)
    }

    private fun customizeLabels(life1: Label, life2: Label, score1: Label, score2: Label) {
        life1.font = Font("Arial", 40.0)
        life1.textFill = Color.WHITE
        life2.font = Font("Arial", 40.0)
        life2.textFill = Color.WHITE
        score1.font = Font("Arial", 40.0)
        score1.textFill = Color.WHITE
        score2.font = Font("Arial", 40.0)
        score2.textFill = Color.WHITE
    }


    private fun buildGeneralPane(life1: Label, life2: Label, score1: Label, score2: Label): StackPane {
        val generalPane = StackPane()

        val livesLayout = buildLivesLayout(life1, life2)
        val scoreLayout = buildScoresLayout(score1, score2)

        addBackground()

        generalPane.children.addAll(facade.view, livesLayout, scoreLayout)
        return generalPane

    }

    private fun addBackground() {
        facade.view.id = "facade"
        facade.view.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        gameScene = Scene(facade.view)
    }



    private fun cleanFacade() {
        facade.showCollider.set(false)
        facade.showGrid.set(false)
    }

    private fun buildLivesLayout(life1: Label, life2: Label): VBox {
        val livesLayout = VBox()
        val lives = Label()
        lives.text = "LIVES"
        lives.font = Font("Arial", 50.0)
        lives.textFill = Color.WHITE
        livesLayout.alignment = Pos.TOP_LEFT
        livesLayout.children.addAll(lives,life1, life2)
        return livesLayout
    }

    private fun buildScoresLayout(score1: Label, score2: Label): VBox {
        val scoreLayout = VBox()
        val scores = Label()
        scores.text = "SCORES:"
        scores.font = Font("Arial", 50.0)
        scores.textFill = Color.WHITE
        scoreLayout.alignment = Pos.TOP_RIGHT
        scoreLayout.children.addAll(scores, score1, score2)
        return scoreLayout
    }


    private fun setWindowSize(window: Stage) {
        window.width = GAME_WIDTH + 40
        window.height = GAME_HEIGHT + 40
    }

    private fun setStartingScene(
        primaryStage: Stage,
        adapter: UIAdapter,
        inserter: EntityInSceneManager,
    ) {
        primaryStage.scene = generateStartScene(adapter, primaryStage, inserter)
    }


    private fun generateStartScene(adapter: UIAdapter, window: Stage, inserter: EntityInSceneManager): Scene {
        val gameTitleLabel = Label("BloonShip")
        val onePlayerButton = generatePlayerButton(1, "One Player", adapter, window, inserter)
        val twoPlayersButton = generatePlayerButton(2, "Two Player", adapter, window, inserter)
        val loadGameButton = generateLoadGameButton("Load Game", adapter, window, inserter)

        val buttonLayout = HBox()
        buttonLayout.children.addAll(loadGameButton, onePlayerButton, twoPlayersButton)

        val completeLayout = VBox()
        completeLayout.children.addAll(gameTitleLabel, buttonLayout)

        return Scene(completeLayout)
    }

    private fun generateLoadGameButton(text: String, adapter: UIAdapter, window: Stage, inserter: EntityInSceneManager): Button {
        val button =  Button(text)
        button.setOnMouseClicked{
            val gameState = adapter.loadGame()
            inserter.updateFacade(gameState)
            stateManager.setState(gameState)
            window.scene = gameScene
        }
        return button
    }

    private fun generatePlayerButton(playerQuantity: Int, text: String, adapter: UIAdapter, window: Stage, inserter: EntityInSceneManager): Button {
        val button =  Button(text)
        button.setOnMouseClicked {
            stateManager.setState(adapter.initializeGame(playerQuantity))
            inserter.updateFacade(stateManager.getState())
            window.scene = gameScene
        }
        return button
    }

    private fun addListeners(inserter: EntityInSceneManager, adapter: UIAdapter, timeListener: TimeListener) : Pauser{
        val pauseManager = Pauser(stateManager, adapter)
        keyTracker.scene = gameScene
        facade.timeListenable.addEventListener(timeListener)
        facade.collisionsListenable.addEventListener(CollisionListener(stateManager, inserter, adapter))
//        facade.outOfBoundsListenable.addEventListener(OutOfBoundsListener(stateManager, inserter, adapter))
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
        addNewElements(map)
        takeOutOldElements(oldMap, map)
    }
    private fun takeOutOldElements(oldMap: MutableMap<String, Collideable>, map: MutableMap<String, Collideable>) {
        for (key in oldMap.keys){
            if (!map.containsKey(key)) removeById(key)
        }
    }

    private fun addNewElements(map: MutableMap<String, Collideable>) {
        for (key in map.keys){
            insert(adapter.transformResultToElementModel(map.get(key)))
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

    fun getState() : GameState {
        return gameState
    }

    fun setState(gameState: GameState){
        this.gameState = gameState
    }
}

class Pauser(private val stateManager: StateManager, private val adapter: UIAdapter){

    private var isPaused = false

    fun activatedKey(){
        if (isPaused) unPause()
        else pause()
    }
    private fun pause() {
        isPaused = true
        stateManager.setState(adapter.pause(stateManager.getState()))
    }
    private fun unPause() {
        isPaused = false
        stateManager.setState(adapter.unPause(stateManager.getState()))
    }
}


class TimeListener(
    private val stateManager: StateManager,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter,
    private var life1: Label,
    private var life2: Label,
    private var score1: Label,
    private var score2: Label
) : EventListener<TimePassed> {

    override fun handle(event: TimePassed) {
        val gameState = adapter.handle(event, stateManager.getState())
        updateLives()
        updateScore()
        inserter.updateFacade(gameState)
        stateManager.setState(gameState)
    }

    private fun updateScore() {
        val newscore1 = adapter.getPlayeScore(1, stateManager.getState())
        if (newscore1 != -1) score1.text = "player 1: $newscore1"
        val newscore2 = adapter.getPlayeScore(2, stateManager.getState())
        if (newscore2 != -1) score2.text = "player 2: $newscore2"
    }

    private fun updateLives() {
        val newLife1 = adapter.getPlayerLives(1, stateManager.getState())
        if (newLife1 != -1) life1.text = "player 1: $newLife1"
        val newLife2 = adapter.getPlayerLives(2, stateManager.getState())
        if (newLife2 != -1) life2.text = "player 2: $newLife2"
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
        if (event.key.toString().equals(PAUSE_GAME_KEY)) pauser.activatedKey()
        if (event.key.toString().equals(SAVE_GAME_KEY)) adapter.save(stateManager.getState())
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
        if (event.id.startsWith("starship")) return
        val gameState = adapter.handle(event, stateManager.getState())
        inserter.removeById(event.id)
        stateManager.setState(gameState)
    }
}