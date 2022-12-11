package starships.movement;

import edu.austral.ingsis.starships.ui.TimePassed;
import org.jetbrains.annotations.NotNull;
import starships.collision.Collideable;
import starships.collision.Handler;
import starships.state.GameState;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class MovementHandler implements Handler<TimePassed> {
    @Override
    public GameState handle(TimePassed event, GameState gameState) {
        return updateGame(event.getSecondsSinceLastTime(), gameState);
    }

    @NotNull
    private GameState updateGame(Double secondsSinceLastTime, GameState gameState) {
        return gameState.addCollideables(calculateUpdates(secondsSinceLastTime, gameState));
    }

    @NotNull
    private Set<Collideable> calculateUpdates(Double secondsSinceLastTime, GameState gameState) {
        Map<String, Collideable> acualCollideablesMap = gameState.getCollideableMap().getAcualCollideablesMap();
        return calculateNewCollideable(secondsSinceLastTime, acualCollideablesMap, gameState);
    }

    @NotNull
    private Set<Collideable> calculateNewCollideable(Double secondsSinceLastTime, Map<String, Collideable> acualCollideablesMap, GameState gameState) {
        Set<Collideable> updates = new HashSet<>();
        for (Collideable collideable : acualCollideablesMap.values()) {
            updates.add(collideable.move(secondsSinceLastTime, gameState.getKeyService()));
        }
        return updates;
    }
}
