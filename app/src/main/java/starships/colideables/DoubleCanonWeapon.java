package starships.colideables;

import starships.collision.ClassicBulletBehavior;
import starships.collision.ClassicBulletCollisionVisitor;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.Vector;

import java.util.Set;
import java.util.UUID;

import static starships.config.Constants.*;

public class DoubleCanonWeapon implements Weapon {

    private final Double secondsSinceLastShot;

    public DoubleCanonWeapon(Double secondsSinceLastShot) {
        this.secondsSinceLastShot = secondsSinceLastShot;
    }

    @Override
    public ShootResult shoot(Starship starship, Double secondsSinceLastTime) {
        if (readyToShoot(secondsSinceLastTime)) return shoot(starship);
        return new ShootResult(new DoubleCanonWeapon(secondsSinceLastShot + secondsSinceLastTime), Set.of());
    }

    private ShootResult shoot(Starship starship) {
        BulletData bulletData = new BulletData(starship.getPlayerNumber(), 1d, DOUBLE_DAMAGE);
        return new ShootResult(
                new DoubleCanonWeapon(0d),
                Set.of(createBullet(starship, bulletData, 90d), createBullet(starship, bulletData, -90d)));
    }

    private Bullet createBullet(Starship starship, BulletData bulletData, Double incline) {
        return new Bullet(
                UUID.randomUUID(),
                new MovementData(
                        starship.getMovementData().getPosition(),
                        new Vector(starship.getMovementData().getAngleInDegrees() + incline, BULLET_SPEED, 0),
                        new Rotation(starship.getMovementData().getAngleInDegrees() + incline, 0d)
                ),
                bulletData,
                new ClassicBulletBehavior(),
                new ClassicBulletCollisionVisitor(bulletData)
        );
    }

    private boolean readyToShoot(Double secondsSinceLastTime) {
        return secondsSinceLastShot + secondsSinceLastTime >= DOUBLE_WEAPON_FIRE_RATE;
    }
}
