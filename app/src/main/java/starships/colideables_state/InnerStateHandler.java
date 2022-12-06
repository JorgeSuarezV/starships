package starships.colideables_state;

import edu.austral.ingsis.starships.ui.TimePassed;
import org.jetbrains.annotations.NotNull;
import starships.collision.Collideable;
import starships.collision.Handler;
import starships.state.GameState;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;


public class InnerStateHandler implements Handler<TimePassed> {

    @Override
    public GameState handle(TimePassed event, GameState gameState) {
        InnerStateVisitor innerStateVisitor = new InnerStateVisitor(event.getSecondsSinceLastTime(), gameState.getKeyService());
        return updateGame(innerStateVisitor, gameState);

    }

    @NotNull
    private GameState updateGame(InnerStateVisitor innerStateVisitor, GameState gameState) {
        return gameState.addCollideables(calculateUpdates(innerStateVisitor, gameState));
    }

    @NotNull
    private Set<Collideable> calculateUpdates(InnerStateVisitor innerStateVisitor, GameState gameState) {
        Map<String, Collideable> acualCollideablesMap = gameState.getCollideableMap().getAcualCollideablesMap();
        Set<Collideable> updates = new HashSet<>();
        for (String id : acualCollideablesMap.keySet()) {
            Set<Collideable> result = acualCollideablesMap.get(id).accept(innerStateVisitor);
            updates.addAll(result);
        }
        return updates;
    }
}
