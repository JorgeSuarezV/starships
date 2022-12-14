package starships.colideables;

import org.jetbrains.annotations.Nullable;
import starships.collision.BulletBehavior;
import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;

import java.util.UUID;

import static starships.movement.MovementService.isOutOfBounds;
import static starships.movement.MovementService.noAccelerationNewMovement;

public class Bullet implements Collideable {

    private final String id;
    private final MovementData movementData;
    private final BulletData bulletData;
    private final BulletBehavior bulletBehavior;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public Bullet(String id, MovementData movementData, BulletData bulletData, BulletBehavior bulletBehavior, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.movementData = movementData;
        this.bulletData = bulletData;
        this.bulletBehavior = bulletBehavior;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public Bullet(MovementData movementData, BulletData bulletData, BulletBehavior bulletBehavior, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = "bullet-" + UUID.randomUUID();
        this.movementData = movementData;
        this.bulletData = bulletData;
        this.bulletBehavior = bulletBehavior;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public String getId() {
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
    public @Nullable
    Collideable move(Double secondsSinceLastTime, KeyService keyService) {
        MovementData movementData = noAccelerationNewMovement(this.movementData, secondsSinceLastTime);
        if (isOutOfBounds(movementData)) return null;
        return new Bullet(
                id,
                movementData,
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
