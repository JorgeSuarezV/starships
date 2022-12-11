package starships.collision;

import starships.colideables.*;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class ClassicBulletCollisionVisitor implements Visitor<CollisionResult> {

    private final BulletData bulletData;

    public ClassicBulletCollisionVisitor(BulletData bulletData) {
        this.bulletData = bulletData;
    }


    @Override
    public CollisionResult visitStarship(Starship starship) {
        if (isFromSameShip(starship, bulletData))
            return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
        return takeOneLiveFromStarship(starship);
    }

    private boolean isFromSameShip(Starship starship, BulletData bulletData) {
        return Objects.equals(starship.getPlayerNumber(), bulletData.getPlayerNumber());
    }

    @Override
    public CollisionResult visitAsteroid(Asteroid asteroid) {
        if (asteroidBreaks(asteroid, bulletData))
            return new CollisionResult(Collections.emptySet(), Set.of(asteroid), calculatePointsToAdd(asteroid, bulletData), bulletData.getPlayerNumber());
        Asteroid newAsteroid = new Asteroid(
                asteroid.getId(),
                asteroid.getMovementData(),
                asteroid.getHealth() - bulletData.getDamage(),
                asteroid.getPoints(),
                asteroid.getCollisionVisitor()
        );
        return new CollisionResult(Set.of(newAsteroid), Collections.emptySet(), 0, 0);
    }

    private Integer calculatePointsToAdd(Asteroid asteroid, BulletData bulletData) {
        return (int) (asteroid.getPoints() * bulletData.getPointMultiplier());
    }

    private boolean asteroidBreaks(Asteroid asteroid, BulletData bulletData) {
        return asteroid.getHealth() - bulletData.getDamage() <= 0;
    }

    @Override
    public CollisionResult visitBullet(Bullet bullet) {
        return new CollisionResult(Collections.emptySet(), Collections.emptySet(), 0, 0);
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
