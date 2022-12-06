package starships.state;

import javafx.scene.input.KeyCode;
import starships.collision.Collideable;
import starships.keys.KeyService;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class GameState {

    private final List<Integer> playerScores;
    private final CollideableMap collideableMap;
    private final KeyService keyService;

    public GameState(List<Integer> playerScores, CollideableMap collideableMap, KeyService keyService) {
        this.playerScores = playerScores;
        this.collideableMap = collideableMap;
        this.keyService = keyService;
    }

    public GameState() {
        this.playerScores = createNewScores();
        this.collideableMap = new CollideableMap();
        this.keyService = new KeyService();
    }

    private List<Integer> createNewScores() {
        List<Integer> newScores = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            newScores.add(0);
        }
        return newScores;
    }

    public CollideableMap getCollideableMap() {
        return collideableMap;
    }

    public KeyService getKeyService() {
        return keyService;
    }

    public GameState addCollideables(Set<Collideable> collideables) {
        return new GameState(playerScores, collideableMap.addCollideables(collideables), keyService);
    }

    public GameState removeCollideables(Set<Collideable> collideablesToRemove) {
        return new GameState(playerScores, collideableMap.removeCollideables(collideablesToRemove), keyService);
    }

    public GameState removeCollideablesByIds(Set<String> ids) {
        return new GameState(playerScores, collideableMap.removeCollideablesByIds(ids), keyService);
    }

    public GameState keyPressed(KeyCode key) {
        return new GameState(playerScores, collideableMap, keyService.keyPressed(key));
    }

    public GameState keyReleased(KeyCode key) {
        return new GameState(playerScores, collideableMap, keyService.keyReleased(key));
    }

    public List<Integer> getFinalScores() {
        return playerScores;
    }

    public GameState addPoints(Integer points, Integer playerNumber) {
        if (points != 0) {
            return new GameState(calculateNewPlayerScores(points, playerNumber), collideableMap, keyService);
        }
        return this;
    }

    private List<Integer> calculateNewPlayerScores(Integer points, Integer playerNumber) {
        List<Integer> newScores = new ArrayList<>(playerScores);
        newScores.set(playerNumber - 1, points + playerScores.get(playerNumber - 1));
        return newScores;
    }
}
