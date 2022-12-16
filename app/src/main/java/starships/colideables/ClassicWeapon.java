package starships.colideables;

import starships.collision.ClassicBulletBehavior;
import starships.collision.ClassicBulletCollisionVisitor;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.Vector;

import java.util.Set;

import static starships.config.Constants.*;

public class ClassicWeapon implements Weapon {

    private final Double secondsSinceLastShot;

    public ClassicWeapon(Double secondsSinceLastShot) {
        this.secondsSinceLastShot = secondsSinceLastShot;
    }

    @Override
    public ShootResult shoot(Starship starship, Double secondsSinceLastTime) {
        if (readyToShoot(secondsSinceLastTime)) return shoot(starship);
        return new ShootResult(new ClassicWeapon(secondsSinceLastShot + secondsSinceLastTime), Set.of());
    }

    private ShootResult shoot(Starship starship) {
        BulletData bulletData = new BulletData(starship.getPlayerNumber(), 1d, CLASSIC_DAMAGE);
        return new ShootResult(
                new ClassicWeapon(0d),
                Set.of(new Bullet(
                        new MovementData(
                                starship.getMovementData().getPosition(),
                                new Vector(starship.getMovementData().getAngleInDegrees() - 90d, BULLET_SPEED, 0),
                                new Rotation(starship.getMovementData().getAngleInDegrees() - 90d, 0d)
                        ),
                        bulletData,
                        new ClassicBulletBehavior(),
                        new ClassicBulletCollisionVisitor(bulletData)
                )));
    }

    private boolean readyToShoot(Double secondsSinceLastTime) {
        return secondsSinceLastShot + secondsSinceLastTime >= CLASSIC_WEAPON_FIRE_RATE;
    }
}
