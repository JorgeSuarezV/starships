package starships.colideables;

import starships.collision.BulletBehavior;
import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;

import java.util.UUID;

import static starships.movement.MovementService.noAccelerationNewMovement;

public class Bullet implements Collideable {

    private final UUID id;
    private final MovementData movementData;
    private final BulletData bulletData;
    private final BulletBehavior bulletBehavior;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public Bullet(UUID id, MovementData movementData, BulletData bulletData, BulletBehavior bulletBehavior, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.movementData = movementData;
        this.bulletData = bulletData;
        this.bulletBehavior = bulletBehavior;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public UUID getId() {
        return id;
    }

    public MovementData getMovementData() {
        return movementData;
    }


    public BulletBehavior getBulletBehavior() {
        return bulletBehavior;
    }

    public Double getDamage() {
        return bulletData.getDamage();
    }

    @Override
    public Visitor<CollisionResult> getCollisionVisitor() {
        return collisionResultVisitor;
    }

    @Override
    public Collideable move(Double secondsSinceLastTime, KeyService keyService) {
        return new Bullet(
                id,
                noAccelerationNewMovement(movementData, secondsSinceLastTime),
                bulletData,
                bulletBehavior,
                collisionResultVisitor
        );
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitBullet(this);
    }

    public BulletData getBulletData() {
        return bulletData;
    }
}
