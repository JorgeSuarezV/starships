package starships.colideables;

import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.movement.MovementData;

import java.util.UUID;

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
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitPowerUp(this);
    }

    public PowerUpApplier getPowerUpAplier() {
        return powerUpAplier;
    }
}
