package starships.spawn;

import starships.colideables.PowerUp;
import starships.colideables.PowerUpApplier;
import starships.colideables.power_up_apliers.DoubleCanonPowerUp;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;
import starships.collision.ClassicPowerUpCollisionVisitor;
import starships.collision.Collideable;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static starships.Util.GeneralUtils.getRandomValue;

public class PowerUpSpawner implements Spawner {

    private final Double spawnRate;
    private final Double maxWidth;
    private final List<PowerUpApplier> powerUpAppliers = generateAppliers();

    public PowerUpSpawner(Double spawnRate, Double maxWidth) {
        this.spawnRate = spawnRate;
        this.maxWidth = maxWidth;
    }

    private List<PowerUpApplier> generateAppliers() {
        List<PowerUpApplier> powerUpAppliers = new ArrayList<>();
        powerUpAppliers.add(new DoubleCanonPowerUp(5d, 0d));
        powerUpAppliers.add(new InvulnerabilityPowerUp(5d, 0d));
        return powerUpAppliers;
    }

    @Override
    public Set<Collideable> spawn(Double secondsSinceLastTime, Double currentTimeInSeconds) {
        if (isSpawnTime(secondsSinceLastTime, currentTimeInSeconds)) return Set.of(spawnPowerUp());
        return Set.of();
    }

    private PowerUp spawnPowerUp() {
        PowerUpApplier powerUpAplier = generateRandomPowerUp();
        return new PowerUp(
                calculateRandomMovementData(),
                powerUpAplier,
                new ClassicPowerUpCollisionVisitor(powerUpAplier)
        );
    }

    private PowerUpApplier generateRandomPowerUp() {
        return powerUpAppliers.get((int) (Math.random() * powerUpAppliers.size()));
    }

    private MovementData calculateRandomMovementData() {
        return new MovementData(
                new Vector(getRandomValue(0d, maxWidth), 0d),
                new Vector(0d, 40d),
                new Rotation(0d, 0d)
        );
    }

    private boolean isSpawnTime(Double secondsSinceLastTime, Double currentTimeInSeconds) {
        return currentTimeInSeconds % spawnRate < secondsSinceLastTime;
    }
}