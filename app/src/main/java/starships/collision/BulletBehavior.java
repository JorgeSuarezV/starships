package starships.collision;

import starships.colideables.Bullet;

public interface BulletBehavior {

    CollisionResult crashBullet(Bullet bullet);
}
