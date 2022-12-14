package starships.colideables;

import org.jetbrains.annotations.Nullable;
import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;

import java.util.UUID;

import static starships.movement.MovementService.isOutOfBounds;
import static starships.movement.MovementService.noAccelerationNewMovement;

public class Asteroid implements Collideable {

    private final String id;
    private final MovementData movementData;
    private final Double health;
    private final Integer points;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public Asteroid(String id, MovementData movementData, Double health, Integer points, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.movementData = movementData;
        this.health = health;
        this.points = points;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public Asteroid(MovementData movementData, Double health, Integer points, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = "asteroid-" + UUID.randomUUID();
        this.movementData = movementData;
        this.health = health;
        this.points = points;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public String getId() {
        return id;
    }

    public MovementData getMovementData() {
        return movementData;
    }

    public Double getHealth() {
        return health;
    }

    public Integer getPoints() {
        return points;
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
        return new Asteroid(
                id,
                movementData,
                health,
                points,
                collisionResultVisitor
        );
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitAsteroid(this);
    }
}
