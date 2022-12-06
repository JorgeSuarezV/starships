package starships.collision;

import starships.colideables.*;

import java.util.Collections;
import java.util.Set;

public class ClassicPowerUpCollisionVisitor implements Visitor<CollisionResult> {

    private final PowerUpApplier powerUpAplier;

    public ClassicPowerUpCollisionVisitor(PowerUpApplier powerUpAplier) {
        this.powerUpAplier = powerUpAplier;
    }

    @Override
    public CollisionResult visitStarship(Starship starship) {
        return new CollisionResult(Set.of(addStarshipPowerUp(starship)), Collections.emptySet(), 0, 0);
    }

    private Starship addStarshipPowerUp(Starship starship) {
        return powerUpAplier.mount(starship);
    }


    @Override
    public CollisionResult visitAsteroid(Asteroid asteroid) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    @Override
    public CollisionResult visitBullet(Bullet bullet) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    @Override
    public CollisionResult visitPowerUp(PowerUp powerUp) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }
}
