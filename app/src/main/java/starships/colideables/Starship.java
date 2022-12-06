package starships.colideables;

import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.movement.MovementData;

import java.util.Set;
import java.util.UUID;

public class Starship implements Collideable {

    private final UUID id;
    private final Integer playerNumber;
    private final Set<PowerUpApplier> starshipPowerUps;
    private final MovementData movementData;
    private final Integer lives;
    private final Weapon weapon;
    private final Visitor<CollisionResult> collisionResultVisitor;

    public Starship(UUID id, Integer playerNumber, Set<PowerUpApplier> starshipPowerUps, MovementData movementData, Integer lives, Weapon weapon, Visitor<CollisionResult> collisionResultVisitor) {
        this.id = id;
        this.playerNumber = playerNumber;
        this.starshipPowerUps = starshipPowerUps;
        this.movementData = movementData;
        this.lives = lives;
        this.weapon = weapon;
        this.collisionResultVisitor = collisionResultVisitor;
    }

    public UUID getId() {
        return id;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Integer getLives() {
        return lives;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    @Override
    public Visitor<CollisionResult> getCollisionVisitor() {
        return collisionResultVisitor;
    }

    @Override
    public <T> T accept(Visitor<T> visitor) {
        return visitor.visitStarship(this);
    }

    public MovementData getMovementData() {
        return movementData;
    }

    public Set<PowerUpApplier> getStarshipPowerUps() {
        return starshipPowerUps;
    }
}
