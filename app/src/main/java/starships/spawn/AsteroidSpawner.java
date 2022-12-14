package starships.spawn;

import starships.colideables.Asteroid;
import starships.collision.ClassicAsteroidCollisionVisitor;
import starships.collision.Collideable;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.Vector;

import java.util.Set;
import java.util.UUID;

import static starships.Util.GeneralUtils.getRandomValue;
import static starships.config.Constants.*;


public class AsteroidSpawner implements Spawner {

    private final Double spawnRate;

    public AsteroidSpawner(Double spawnRate) {
        this.spawnRate = spawnRate;
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
        double randomValue = getRandomValue(MIN_ASTEROID_SIZE, MAX_ASTEROID_SIZE);
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
            return new Vector(getRandomValue(0d, GAME_WIDTH), 0d);
        } else {
            return new Vector(0d, getRandomValue(0d, GAME_HEIGHT));
        }
    }

    private Vector calculateRandomCenterPosition() {
        Double xPosition = getRandomValue(GAME_WIDTH / 4, (GAME_WIDTH / 4) * 3);
        Double yPosition = getRandomValue(GAME_HEIGHT / 4, (GAME_HEIGHT / 4) * 3);
        return new Vector(xPosition, yPosition);
    }

    private boolean isSpawnTime(Double secondsSinceLastTime, Double currentTimeInSeconds) {
        return currentTimeInSeconds % spawnRate < secondsSinceLastTime;
    }
}
