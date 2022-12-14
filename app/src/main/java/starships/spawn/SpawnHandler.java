package starships.spawn;

import edu.austral.ingsis.starships.ui.TimePassed;
import org.jetbrains.annotations.NotNull;
import starships.collision.Collideable;
import starships.collision.Handler;
import starships.state.GameState;

import java.util.Set;

import static starships.Util.Sets.mergeSet;

public class SpawnHandler implements Handler<TimePassed> {

    private final Spawner asteroidSpawner;
    private final Spawner powerUpSpawner;

    public SpawnHandler(Double maxPowerUpWidth, Double maxHealth, Double minHealth, Double maxWidth, Double maxHeight) {
        this.asteroidSpawner = new AsteroidSpawner(3d, maxHealth, minHealth, maxWidth, maxHeight);
        this.powerUpSpawner = new PowerUpSpawner(20d, maxPowerUpWidth);
    }

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
