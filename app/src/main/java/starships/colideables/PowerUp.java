package starships.colideables;

import org.jetbrains.annotations.Nullable;
import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;

import java.util.UUID;

import static starships.movement.MovementService.isOutOfBounds;
import static starships.movement.MovementService.noAccelerationNewMovement;

public class PowerUp implements Collideable {

    private final String id;
    private final MovementData movementData;
    private final PowerUpApplier powerUpApplier;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public PowerUp(String id, MovementData movementData, PowerUpApplier powerUpApplier, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.movementData = movementData;
        this.powerUpApplier = powerUpApplier;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public PowerUp(MovementData movementData, PowerUpApplier powerUpApplier, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = "powerUp-" + UUID.randomUUID();
        this.movementData = movementData;
        this.powerUpApplier = powerUpApplier;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public String getId() {
        return id;
    }

    public MovementData getMovementData() {
        return movementData;
    }


    public Visitor<CollisionResult> getCollisionResultVisitor() {
        return collisionResultVisitor;
    }

    @Override
    public Visitor<CollisionResult> getCollisionVisitor() {
        return collisionResultVisitor;
    }

    @Override
    public @Nullable
    Collideable move(Double secondsSinceLastTime, KeyService keyService) {
        MovementData movementData = noAccelerationNewMovement(this.movementData, secondsSinceLastTime);
        if (isOutOfBounds(movementData)) return null;
        return new PowerUp(
                id,
                movementData,
                powerUpApplier,
                collisionResultVisitor
        );
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPowerUp(this);
    }

    public PowerUpApplier getPowerUpApplier() {
        return powerUpApplier;
    }
}
