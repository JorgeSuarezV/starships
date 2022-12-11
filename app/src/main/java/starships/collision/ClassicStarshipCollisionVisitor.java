package starships.collision;

import starships.colideables.*;

import java.util.Collections;
import java.util.Set;

public class ClassicStarshipCollisionVisitor implements Visitor<CollisionResult> {

    private final Integer playerNumber;

    public ClassicStarshipCollisionVisitor(Integer playerNumber) {
        this.playerNumber = playerNumber;
    }

    @Override
    public CollisionResult visitStarship(Starship starship) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    @Override
    public CollisionResult visitAsteroid(Asteroid asteroid) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    @Override
    public CollisionResult visitBullet(Bullet bullet) {
        if (playerNumber.equals(bullet.getBulletData().getPlayerNumber()))
            return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
        return bullet.getBulletBehavior().crashBullet(bullet);
    }

    @Override
    public CollisionResult visitPowerUp(PowerUp powerUp) {
        return new CollisionResult(Collections.emptySet(), Set.of(powerUp), 0, 0);

    }
}
