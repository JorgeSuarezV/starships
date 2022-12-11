package starships.collision;

import starships.colideables.*;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;

import java.util.Collections;
import java.util.Set;

public class ClassicAsteroidCollisionVisitor implements Visitor<CollisionResult> {

    @Override
    public CollisionResult visitStarship(Starship starship) {
        return takeOneLiveFromStarship(starship);
    }

    @Override
    public CollisionResult visitAsteroid(Asteroid asteroid) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    @Override
    public CollisionResult visitBullet(Bullet bullet) {
        return bullet.getBulletBehavior().crashBullet(bullet);
    }

    @Override
    public CollisionResult visitPowerUp(PowerUp powerUp) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
    }

    private CollisionResult takeOneLiveFromStarship(Starship starship) {
        if (starshipBreaks(starship)) return new CollisionResult(Collections.emptySet(), Set.of(starship), 0, 0);
        return new CollisionResult(Set.of(calculateNewStarship(starship)), Collections.emptySet(), 0, 0);
    }

    private Starship calculateNewStarship(Starship starship) {
        Starship starship1 = addInvulnerabilityToStarship(starship);
        return new Starship(
                starship1.getId(),
                starship1.getPlayerNumber(),
                starship1.getStarshipPowerUps(),
                starship1.getMovementData(),
                starship1.getLives() - 1,
                starship1.getWeapon(),
                starship1.getCollisionVisitor(),
                starship.getMover());
    }

    private Starship addInvulnerabilityToStarship(Starship starship) {
        return new InvulnerabilityPowerUp(starship.getLives() - 1, 3d, 0d).mount(starship);
    }

    private boolean starshipBreaks(Starship starship) {
        return starship.getLives() - 1 <= 0;
    }
}
