package starships.colideables;

import starships.collision.Collideable;
import starships.collision.CollisionResult;
import starships.keys.KeyService;
import starships.movement.MovementData;
import starships.movement.Mover;

import java.util.Set;
import java.util.UUID;

public class Starship implements Collideable {

    private final String id;
    private final Integer playerNumber;
    private final Set<PowerUpApplier> starshipPowerUps;
    private final MovementData movementData;
    private final Integer lives;
    private final Weapon weapon;
    private final Visitor<CollisionResult> collisionResultVisitor;
    private final Mover mover;

    public Starship(String id, Integer playerNumber, Set<PowerUpApplier> starshipPowerUps, MovementData movementData, Integer lives, Weapon weapon, Visitor<CollisionResult> collisionResultVisitor, Mover mover) {
        this.id = id;
        this.playerNumber = playerNumber;
        this.starshipPowerUps = starshipPowerUps;
        this.movementData = movementData;
        this.lives = lives;
        this.weapon = weapon;
        this.collisionResultVisitor = collisionResultVisitor;
        this.mover = mover;
    }
    public Starship(Integer playerNumber, Set<PowerUpApplier> starshipPowerUps, MovementData movementData, Integer lives, Weapon weapon, Visitor<CollisionResult> collisionResultVisitor, Mover mover) {
        this.id = "starship-" + UUID.randomUUID();
        this.playerNumber = playerNumber;
        this.starshipPowerUps = starshipPowerUps;
        this.movementData = movementData;
        this.lives = lives;
        this.weapon = weapon;
        this.collisionResultVisitor = collisionResultVisitor;
        this.mover = mover;
    }

    public String getId() {
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
    public Collideable move(Double secondsSinceLastTime, KeyService keyService) {
        return new Starship(
                id,
                playerNumber,
                starshipPowerUps,
                mover.move(secondsSinceLastTime, keyService, playerNumber, movementData),
                lives,
                weapon,
                collisionResultVisitor,
                mover
        );
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

    public Mover getMover() {
        return mover;
    }
}
