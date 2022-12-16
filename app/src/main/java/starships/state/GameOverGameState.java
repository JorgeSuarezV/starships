package starships.state;

import javafx.scene.input.KeyCode;
import starships.collision.Collideable;
import starships.keys.KeyService;

import java.util.Set;

public class GameOverGameState implements GameState {

    private final PausedGameState pausedGameState;

    public GameOverGameState(PausedGameState pausedGameState) {
        this.pausedGameState = pausedGameState;
    }


    @Override
    public CollideableMap getCollideableMap() {
        return pausedGameState.getCollideableMap();
    }

    @Override
    public KeyService getKeyService() {
        return pausedGameState.getKeyService();
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
        return pausedGameState.getScores();
    }

    @Override
    public GameState addPoints(Integer points, Integer playerNumber) {
        return this;
    }

    @Override
    public Integer getPlayerScore(Integer playerNumber) {
        return pausedGameState.getPlayerScore(playerNumber);
    }

    @Override
    public Integer getPlayerQuantity() {
        return pausedGameState.getPlayerQuantity();
    }

    @Override
    public Integer getLives(Integer playerLives) {
        return pausedGameState.getLives(playerLives);
    }

    @Override
    public Boolean isOver() {
        return true;
    }
}
