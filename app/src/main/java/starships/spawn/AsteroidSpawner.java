package starships.spawn;

import starships.colideables.Asteroid;
import starships.collision.ClassicAsteroidCollisionVisitor;
import starships.collision.Collideable;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.Vector;

import java.util.Set;

import static starships.Util.GeneralUtils.getRandomValue;
import static starships.config.Constants.ASTEROID_SPEED_COEFFICIENT;


public class AsteroidSpawner implements Spawner {

    private final Double spawnRate;
    private final Double maxHealth;
    private final Double minHealth;
    private final Double maxWidth;
    private final Double maxHeight;

    public AsteroidSpawner(Double spawnRate, Double maxHealth, Double minHealth, Double maxWidth, Double maxHeight) {
        this.spawnRate = spawnRate;
        this.maxHealth = maxHealth;
        this.minHealth = minHealth;
        this.maxWidth = maxWidth;
        this.maxHeight = maxHeight;
    }

    private static Vector calculateNewSpeed(Vector initialPosition, Vector targetPosition) {
        return new Vector(targetPosition.getX() - initialPosition.getX(), targetPosition.getY() - initialPosition.getY()).multiply(ASTEROID_SPEED_COEFFICIENT);
    }

    @Override
    public Set<Collideable> spawn(Double secondsSinceLastTime, Double currentTimeInSeconds) {
        if (isSpawnTime(secondsSinceLastTime, currentTimeInSeconds)) return Set.of(spawnAsteroid());
        return Set.of();
    }

    private Asteroid spawnAsteroid() {
        double randomValue = getRandomValue(minHealth, maxHealth);
        return new Asteroid(
                calculateRandomMovementData(),
                randomValue,
                (int) randomValue,
                new ClassicAsteroidCollisionVisitor()
        );
    }

    private MovementData calculateRandomMovementData() {
        Vector initialPosition = calculateRandomBorderPosition();
        Vector targetPosition = calculateRandomCenterPosition();
        Vector speed = calculateNewSpeed(initialPosition, targetPosition);
        Rotation rotation = new Rotation(0d, 0d);
        return new MovementData(initialPosition, speed, rotation);
    }

    private Vector calculateRandomBorderPosition() {
        if (Math.random() < 0.5) {
            return new Vector(getRandomValue(0d, maxWidth), 0d);
        } else {
            return new Vector(0d, getRandomValue(0d, maxHeight));
        }
    }

    private Vector calculateRandomCenterPosition() {
        Double xPosition = getRandomValue(maxWidth / 4, (maxWidth / 4) * 3);
        Double yPosition = getRandomValue(maxHeight / 4, (maxHeight / 4) * 3);
        return new Vector(xPosition, yPosition);
    }

    private boolean isSpawnTime(Double secondsSinceLastTime, Double currentTimeInSeconds) {
        return currentTimeInSeconds % spawnRate < secondsSinceLastTime;
    }
}
