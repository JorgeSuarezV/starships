package starships.spawn;

import starships.collision.Collideable;

import java.util.Set;

public interface Spawner {

    Set<Collideable> spawn(Double secondsSinceLastTime, Double currentTimeInSeconds);
}
