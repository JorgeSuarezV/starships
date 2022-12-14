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
        MoveResult moveResult = calculateUpdates(secondsSinceLastTime, gameState);
        GameState gameState1 = gameState.addCollideables(moveResult.getUpdates());
        return gameState1.removeCollideables(moveResult.getRemoves());
    }

    @NotNull
    private MoveResult calculateUpdates(Double secondsSinceLastTime, GameState gameState) {
        Map<String, Collideable> acualCollideablesMap = gameState.getCollideableMap().getAcualCollideablesMap();
        return calculateNewCollideable(secondsSinceLastTime, acualCollideablesMap, gameState);
    }

    @NotNull
    private MoveResult calculateNewCollideable(Double secondsSinceLastTime, Map<String, Collideable> acualCollideablesMap, GameState gameState) {
        Set<Collideable> updates = new HashSet<>();
        Set<Collideable> removes = new HashSet<>();
        for (Collideable collideable : acualCollideablesMap.values()) {
            Collideable newCollideable = collideable.move(secondsSinceLastTime, gameState.getKeyService());
            if (newCollideable == null) removes.add(collideable);
            else updates.add(newCollideable);
        }
        return new MoveResult(updates, removes);
    }
}

class MoveResult {
    private final Set<Collideable> updates;
    private final Set<Collideable> removes;

    MoveResult(Set<Collideable> updates, Set<Collideable> removes) {
        this.updates = updates;
        this.removes = removes;
    }

    public Set<Collideable> getRemoves() {
        return removes;
    }

    public Set<Collideable> getUpdates() {
        return updates;
    }
}
