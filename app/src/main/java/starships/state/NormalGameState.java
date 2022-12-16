package starships.state;

import javafx.scene.input.KeyCode;
import starships.colideables.Starship;
import starships.collision.Collideable;
import starships.keys.KeyService;

import java.util.HashMap;
import java.util.Set;

public class NormalGameState implements GameState {

    private final ScoreService playerScores;
    private final CollideableMap collideableMap;
    private final KeyService keyService;
    private final Integer playerQuantity;

    public NormalGameState(ScoreService playerScores, CollideableMap collideableMap, KeyService keyService, Integer playerQuantity) {
        this.playerScores = playerScores;
        this.collideableMap = collideableMap;
        this.keyService = keyService;
        this.playerQuantity = playerQuantity;
    }

    public NormalGameState(Integer playerQuantity) {
        this.playerScores = new ScoreService(new HashMap<>());
        this.collideableMap = new CollideableMap();
        this.keyService = new KeyService();
        this.playerQuantity = playerQuantity;
    }


    @Override
    public CollideableMap getCollideableMap() {
        return collideableMap;
    }

    @Override
    public KeyService getKeyService() {
        return keyService;
    }

    @Override
    public GameState addCollideables(Set<Collideable> collideables) {
        return new NormalGameState(playerScores, collideableMap.addCollideables(collideables), keyService, playerQuantity);
    }

    @Override
    public GameState removeCollideables(Set<Collideable> collideablesToRemove) {
        return new NormalGameState(playerScores, collideableMap.removeCollideables(collideablesToRemove), keyService, playerQuantity);
    }

    @Override
    public GameState removeCollideablesByIds(Set<String> ids) {
        return new NormalGameState(playerScores, collideableMap.removeCollideablesByIds(ids), keyService, playerQuantity);
    }

    @Override
    public GameState keyPressed(KeyCode key) {
        return new NormalGameState(playerScores, collideableMap, keyService.keyPressed(key), playerQuantity);
    }

    @Override
    public GameState keyReleased(KeyCode key) {
        return new NormalGameState(playerScores, collideableMap, keyService.keyReleased(key), playerQuantity);
    }

    @Override
    public ScoreService getScores() {
        return playerScores;
    }

    @Override
    public GameState addPoints(Integer points, Integer playerNumber) {
        if (points != 0) {
            return new NormalGameState(calculateNewPlayerScores(points, playerNumber), collideableMap, keyService, playerQuantity);
        }
        return this;
    }

    public ScoreService calculateNewPlayerScores(Integer points, Integer playerNumber) {
        return playerScores.addPoints(playerNumber, points);
    }

    @Override
    public Integer getPlayerScore(Integer playerNumber) {
        if (playerNumber > playerQuantity) return -1;
        return playerScores.getPlayerScore(playerNumber);
    }

    @Override
    public Integer getPlayerQuantity() {
        return playerQuantity;
    }

    @Override
    public Integer getLives(Integer playerNumber) {
        if (playerNumber > playerQuantity) return -1;
        for (String id : collideableMap.getAcualCollideablesMap().keySet()) {
            if (isStarship(id) && isPlayer(playerNumber, id))
                return ((Starship) collideableMap.getAcualCollideablesMap().get(id)).getLives();
        }
        return 0;
    }

    @Override
    public Boolean isOver() {
        return false;
    }

    private boolean isPlayer(Integer playerNumber, String id) {
        return ((Starship) collideableMap.getAcualCollideablesMap().get(id)).getPlayerNumber().equals(playerNumber);
    }

    private boolean isStarship(String id) {
        return id.startsWith("starship");
    }

}
