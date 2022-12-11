package starships.colideables;

import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;

import java.util.UUID;

import static starships.movement.MovementService.noAccelerationNewMovement;

public class PowerUp implements Collideable {

    private final UUID id;
    private final MovementData movementData;
    private final PowerUpApplier powerUpAplier;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public PowerUp(UUID id, MovementData movementData, PowerUpApplier powerUpAplier, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.movementData = movementData;
        this.powerUpAplier = powerUpAplier;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public UUID getId() {
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
    public Collideable move(Double secondsSinceLastTime, KeyService keyService) {
        return new PowerUp(
                id,
                noAccelerationNewMovement(movementData, secondsSinceLastTime),
                powerUpAplier,
                collisionResultVisitor
        );
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPowerUp(this);
    }

    public PowerUpApplier getPowerUpAplier() {
        return powerUpAplier;
    }
}
