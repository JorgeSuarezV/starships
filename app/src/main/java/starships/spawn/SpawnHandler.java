package starships.spawn;

import edu.austral.ingsis.starships.ui.TimePassed;
import org.jetbrains.annotations.NotNull;
import starships.collision.Collideable;
import starships.collision.Handler;
import starships.state.GameState;

import java.util.Set;

import static starships.Util.Sets.mergeSet;

public class SpawnHandler implements Handler<TimePassed> {

    private final Spawner asteroidSpawner = new AsteroidSpawner(3d);
    private final Spawner powerUpSpawner = new PowerUpSpawner(20d);

    @Override
    public GameState handle(TimePassed event, GameState gameState) {
        return gameState.addCollideables(spawnCollideables(event));
    }

    @NotNull
    private Set<Collideable> spawnCollideables(TimePassed event) {
        return mergeSet(
                asteroidSpawner.spawn(event.getSecondsSinceLastTime(), event.getCurrentTimeInSeconds()),
                powerUpSpawner.spawn(event.getSecondsSinceLastTime(), event.getCurrentTimeInSeconds())
        );
    }
}
