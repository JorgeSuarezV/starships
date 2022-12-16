package starships.adapter;

import edu.austral.ingsis.starships.ui.*;
import starships.colideables.Visitor;
import starships.colideables_state.InnerStateHandler;
import starships.collision.Collideable;
import starships.collision.CollisionHandler;
import starships.collision.Handler;
import starships.movement.MovementHandler;
import starships.save.LoadHandler;
import starships.save.SaveHandler;
import starships.spawn.SpawnHandler;
import starships.spawn.StarshipInitializer;
import starships.state.GameState;
import starships.state.NormalGameState;
import starships.state.PausedGameState;

import java.util.Set;

import static starships.config.Constants.GAME_HEIGHT;
import static starships.config.Constants.GAME_WIDTH;


public class UIAdapter {

    private final Visitor<ElementModel> adapterVisitor = new AdapterVisitor();
    private final StarshipInitializer starshipInitializer = new StarshipInitializer();
    private final Handler<Collision> collisionHandler = new CollisionHandler();
    private final Handler<TimePassed> spawnHandler = new SpawnHandler(GAME_WIDTH, 130d, 50d, GAME_WIDTH, GAME_HEIGHT);
    private final Handler<TimePassed> innerStateHandler = new InnerStateHandler();
    private final Handler<TimePassed> movementHandler = new MovementHandler();
    private final SaveHandler saveHandler = new SaveHandler();
    private final LoadHandler loadHadler = new LoadHandler();


    public GameState initializeGame(Integer playerNumber) {
        Set<Collideable> collideables = starshipInitializer.spawnStarships(playerNumber);
        return new NormalGameState(playerNumber).addCollideables(collideables);
    }

    public GameState handle(Collision collision, GameState gameState) {
        return collisionHandler.handle(collision, gameState);

    }

    public GameState handle(TimePassed timePassed, GameState gameState) {
        return calculateUpdates(timePassed, gameState);
    }

    public GameState handle(OutOfBounds outOfBounds, GameState gameState) {
        return gameState.removeCollideablesByIds(Set.of(outOfBounds.getId()));
    }

    public GameState handle(KeyPressed keyPressed, GameState gameState) {
        return gameState.keyPressed(keyPressed.getKey());
    }

    public GameState handle(KeyReleased keyReleased, GameState gameState) {
        return gameState.keyReleased(keyReleased.getKey());
    }

    public GameState loadGame() {
        return loadHadler.handle();
    }

    public GameState pause(GameState gameState) {
        return new PausedGameState(gameState);
    }

    public GameState unPause(GameState gameState) {
        return new NormalGameState(gameState.getScores(), gameState.getCollideableMap(), gameState.getKeyService(), gameState.getPlayerQuantity());
    }

    public Integer getPlayerLives(Integer playerNumber, GameState gameState) {
        return gameState.getLives(playerNumber);
    }

    public Integer getPlayeScore(Integer playerNumber, GameState gameState) {
        return gameState.getPlayerScore(playerNumber);
    }

    public GameState save(GameState gameState) {
        return saveHandler.handle(gameState);
    }


    public ElementModel transformResultToElementModel(Collideable collideables) {
        return collideables.accept(adapterVisitor);
    }

    private GameState calculateUpdates(TimePassed timePassed, GameState gameState) {
        GameState gameState2 = spawnHandler.handle(timePassed, gameState);
        GameState gameState3 = innerStateHandler.handle(timePassed, gameState2);
        return movementHandler.handle(timePassed, gameState3);
    }
}
