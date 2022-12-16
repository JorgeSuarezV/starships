package edu.austral.ingsis.starships

import edu.austral.ingsis.starships.ui.*
import javafx.application.Application
import javafx.application.Application.launch
import javafx.geometry.Pos
import javafx.scene.Scene
import javafx.scene.control.Button
import javafx.scene.control.Label
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
private val gameStateManager = StateManager(PausedGameState(NormalGameState(1)) as GameState)



fun main() {
    launch(MyStarships::class.java)
}
class MyStarships() : Application() {

    private var gameScene = Scene(StackPane())

    override fun start(primaryStage: Stage) {
        setWindowSize(primaryStage)
        cleanFacade()
        val adapter = UIAdapter()
        val inserter = EntityInSceneManager(gameStateManager, facade, adapter)

        setStartingScene(primaryStage, adapter, inserter)
        val lifeLabels = customizeLifeLabels()
        val scoreLabels = customizeScoresLabels()
        gameScene = Scene(buildGeneralPane(lifeLabels, scoreLabels))
        val timeListener = TimeListener(gameStateManager, inserter, adapter, lifeLabels, scoreLabels, primaryStage)
        addListeners(inserter, adapter, timeListener)
        startGame(primaryStage)
    }

    private fun customizeLifeLabels() : List<Label>{
        val life1 = Label("")
        val life2 = Label("")
        life1.font = Font("Arial", 40.0)
        life1.textFill = Color.WHITE
        life2.font = Font("Arial", 40.0)
        life2.textFill = Color.WHITE
        return listOf(life1, life2)
    }
    
    private fun customizeScoresLabels() : List<Label>{
        val score1 = Label("")
        val score2 = Label("")
        score1.font = Font("Arial", 40.0)
        score1.textFill = Color.WHITE
        score2.font = Font("Arial", 40.0)
        score2.textFill = Color.WHITE
        return listOf(score1, score2)
    }


    private fun buildGeneralPane(lifeLabels: List<Label>, scoreLabels: List<Label>): StackPane {
        val generalPane = StackPane()

        val livesLayout = buildLivesLayout(lifeLabels)
        val scoreLayout = buildScoresLayout(scoreLabels)

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

    private fun buildLivesLayout(lifeLabels: List<Label>): VBox {
        val livesLayout = VBox()
        val lives = Label()
        lives.text = "LIVES"
        lives.font = Font("Arial", 50.0)
        lives.textFill = Color.WHITE
        livesLayout.alignment = Pos.TOP_LEFT
        livesLayout.children.addAll(lives)
        livesLayout.children.addAll(lifeLabels)
        return livesLayout
    }

    private fun buildScoresLayout(scoreLabels: List<Label>): VBox {
        val scoreLayout = VBox()
        val scores = Label()
        scores.text = "SCORES:"
        scores.font = Font("Arial", 50.0)
        scores.textFill = Color.WHITE
        scoreLayout.alignment = Pos.TOP_RIGHT
        scoreLayout.children.addAll(scores)
        scoreLayout.children.addAll(scoreLabels)
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
        val loadGameButton = generateLoadGameButton(adapter, window, inserter)

        val buttonLayout = HBox()
        buttonLayout.children.addAll(loadGameButton, onePlayerButton, twoPlayersButton)

        val completeLayout = VBox()
        completeLayout.children.addAll(gameTitleLabel, buttonLayout)

        return Scene(completeLayout)
    }

    private fun generateLoadGameButton(adapter: UIAdapter, window: Stage, inserter: EntityInSceneManager): Button {
        val button =  Button("Load Game")
        button.setOnMouseClicked{
            val gameState = adapter.loadGame()
            inserter.updateFacade(gameState)
            gameStateManager.setState(gameState)
            window.scene = gameScene
        }
        return button
    }

    private fun generatePlayerButton(playerQuantity: Int, text: String, adapter: UIAdapter, window: Stage, inserter: EntityInSceneManager): Button {
        val button =  Button(text)
        button.setOnMouseClicked {
            gameStateManager.setState(adapter.initializeGame(playerQuantity))
            inserter.updateFacade(gameStateManager.getState())
            window.scene = gameScene
        }
        return button
    }

    private fun addListeners(inserter: EntityInSceneManager, adapter: UIAdapter, timeListener: TimeListener) : Pauser{
        val pauseManager = Pauser(gameStateManager, adapter)
        keyTracker.scene = gameScene
        facade.timeListenable.addEventListener(timeListener)
        facade.collisionsListenable.addEventListener(CollisionListener(gameStateManager, inserter, adapter))
        keyTracker.keyPressedListenable.addEventListener(KeyPressedListener(gameStateManager, pauseManager, inserter, adapter))
        keyTracker.keyReleasedListenable.addEventListener(KeyReleasedListener(gameStateManager, inserter, adapter))
        return pauseManager
    }

    private fun startGame(primaryStage: Stage) {
        facade.start()
        keyTracker.start()
        primaryStage.show()
    }
}
class EntityInSceneManager(
    private val gameStateManager: StateManager<GameState>,
    private val facade: ElementsViewFacade,
    private val adapter: UIAdapter
){

    fun updateFacade(newGameState: GameState){
        val map = newGameState.collideableMap.acualCollideablesMap
        val oldMap = gameStateManager.getState().collideableMap.acualCollideablesMap
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

class StateManager<T>(private var state: T){

    fun getState() : T {
        return state
    }

    fun setState(newState: T){
        this.state = newState
    }
}

class Pauser(private val gameStateManager: StateManager<GameState>, private val adapter: UIAdapter){

    private var isPaused = false

    fun activatedKey(){
        if (isPaused) unPause()
        else pause()
    }
    private fun pause() {
        isPaused = true
        gameStateManager.setState(adapter.pause(gameStateManager.getState()))
    }
    private fun unPause() {
        isPaused = false
        gameStateManager.setState(adapter.unPause(gameStateManager.getState()))
    }
}


class TimeListener(
    private val gameStateManager: StateManager<GameState>,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter,
    private val lifeLabels: List<Label>,
    private var scoreLabels: List<Label>,
    private val primaryStage: Stage,
) : EventListener<TimePassed> {

    override fun handle(event: TimePassed) {
        val gameState = adapter.handle(event, gameStateManager.getState())
        if (gameState.isOver) primaryStage.scene = createWinningScene(getScoreMap())
        updateLives()
        updateScore()
        inserter.updateFacade(gameState)
        gameStateManager.setState(gameState)
    }

    private fun getScoreMap(): Map<Int, Int> {
        val scores = mutableMapOf<Int, Int>()
        for(i in 1..gameStateManager.getState().playerQuantity) {
            scores.put(i, gameStateManager.getState().getPlayerScore(i))
        }
        return scores
    }

    private fun createWinningScene(scores : Map<Int, Int>): Scene {
        val gameOverLabel = createGameOverLabel()
        val scoresLabel = createScoresLabel(scores)
        val layout = createLayout(gameOverLabel, scoresLabel)
        return Scene(layout)
    }

    private fun createScoresLabel(scores: Map<Int, Int>): Label {
        val scoresLabel = Label(generateScoresString(scores))
        scoresLabel.font = Font("Arial", 60.0)
        scoresLabel.textFill = Color.WHITE
        return scoresLabel
    }

    private fun createGameOverLabel(): Label {
        val gameOverLabel = Label("Scores")
        gameOverLabel.font = Font("Arial", 60.0)
        gameOverLabel.textFill = Color.WHITE
        return gameOverLabel
    }

    private fun createLayout(
        gameOverLabel: Label,
        scoresLabel: Label
    ): VBox {
        val layout = VBox()
        layout.alignment = Pos.CENTER
        layout.id = "facade"
        layout.stylesheets.add(this::class.java.classLoader.getResource("styles.css")?.toString())
        layout.children.addAll(gameOverLabel, scoresLabel)
        return layout
    }

    private fun generateScoresString(scores: Map<Int, Int>): String {
        var body = ""
        for (i in 1..scores.keys.size) {
            body += "player $i: ${scores[i]} \n"
        }
        return body
    }

    private fun updateScore() {
        for(i in 1..gameStateManager.getState().playerQuantity) {
            val newscorei = adapter.getPlayeScore(i, gameStateManager.getState())
            scoreLabels[i-1].text = "player $i:$newscorei"
        }

//        val newscore1 = adapter.getPlayeScore(1, gameStateManager.getState())
//        if (newscore1 != -1) score1.text = "player 1: $newscore1"
//        val newscore2 = adapter.getPlayeScore(2, gameStateManager.getState())
//        if (newscore2 != -1) score2.text = "player 2: $newscore2"
    }

    private fun updateLives() {
        for(i in 1..gameStateManager.getState().playerQuantity){
            val newLifei = adapter.getPlayerLives(i, gameStateManager.getState())
            if (newLifei != -1) lifeLabels[i-1].text = "player $i:$newLifei"
        }
//        val newLife1 = adapter.getPlayerLives(1, gameStateManager.getState())
//        if (newLife1 != -1) life1.text = "player 1: $newLife1"
//        val newLife2 = adapter.getPlayerLives(2, gameStateManager.getState())
//        if (newLife2 != -1) life2.text = "player 2: $newLife2"
    }
}

class CollisionListener(
    private var gameStateManager: StateManager<GameState>,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<Collision> {
    override fun handle(event: Collision) {
        val gameState = adapter.handle(event, gameStateManager.getState())
        inserter.updateFacade(gameState)
        gameStateManager.setState(gameState)
    }
}

class KeyPressedListener(
    private var gameStateManager: StateManager<GameState>,
    private val pauser: Pauser,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<KeyPressed> {

    override fun handle(event: KeyPressed) {
        if (event.key.toString().equals(PAUSE_GAME_KEY)) pauser.activatedKey()
        if (event.key.toString().equals(SAVE_GAME_KEY)) adapter.save(gameStateManager.getState())
        val gameState = adapter.handle(event, gameStateManager.getState())
        inserter.updateFacade(gameState)
        gameStateManager.setState(gameState)
    }
}

class KeyReleasedListener(
    private var gameStateManager: StateManager<GameState>,
    private val inserter: EntityInSceneManager,
    private val adapter: UIAdapter
) : EventListener<KeyReleased> {
    override fun handle(event: KeyReleased) {
        val gameState = adapter.handle(event, gameStateManager.getState())
        inserter.updateFacade(gameState)
        gameStateManager.setState(gameState)
    }
}
