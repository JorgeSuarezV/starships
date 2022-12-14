package starships.state;

import javafx.scene.input.KeyCode;
import starships.collision.Collideable;
import starships.keys.KeyService;

import java.util.Set;

public class PausedGameState implements GameState {

    private final GameState gameState;

    public PausedGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public PausedGameState() {
        this.gameState = new NormalGameState(getPlayerQuantity());
    }

    @Override
    public CollideableMap getCollideableMap() {
        return gameState.getCollideableMap();
    }

    @Override
    public KeyService getKeyService() {
        return gameState.getKeyService();
    }

    @Override
    public GameState addCollideables(Set<Collideable> collideables) {
        return this;
    }

    @Override
    public GameState removeCollideables(Set<Collideable> collideablesToRemove) {
        return this;
    }

    @Override
    public GameState removeCollideablesByIds(Set<String> ids) {
        return this;
    }

    @Override
    public GameState keyPressed(KeyCode key) {
        return this;
    }

    @Override
    public GameState keyReleased(KeyCode key) {
        return this;
    }

    @Override
    public ScoreService getScores() {
        return gameState.getScores();
    }

    @Override
    public GameState addPoints(Integer points, Integer playerNumber) {
        return this;
    }

    @Override
    public Integer getPlayerScore(Integer playerNumber) {
        return gameState.getPlayerScore(playerNumber);
    }

    @Override
    public Integer getPlayerQuantity() {
        return gameState.getPlayerQuantity();
    }

    @Override
    public Integer getLives(Integer playerLives) {
        return gameState.getLives(playerLives);
    }
}
