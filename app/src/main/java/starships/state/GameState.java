package starships.state;

import javafx.scene.input.KeyCode;
import starships.collision.Collideable;
import starships.keys.KeyService;

import java.util.Set;

public interface GameState {

    CollideableMap getCollideableMap();

    KeyService getKeyService();

    GameState addCollideables(Set<Collideable> collideables);

    GameState removeCollideables(Set<Collideable> collideablesToRemove);

    GameState removeCollideablesByIds(Set<String> ids);

    GameState keyPressed(KeyCode key);

    GameState keyReleased(KeyCode key);

    ScoreService getScores();

    GameState addPoints(Integer points, Integer playerNumber);

    ScoreService calculateNewPlayerScores(Integer points, Integer playerNumber);

    Integer getPlayerScore(Integer playerNumber);

    Integer getPlayerQuantity();

    Integer getLives(Integer playerLives);
}
