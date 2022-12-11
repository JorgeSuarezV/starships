package starships.collision;

import starships.colideables.Bullet;

import java.util.Collections;
import java.util.Set;

public class ClassicBulletBehavior implements BulletBehavior {

    @Override
    public CollisionResult crashBullet(Bullet bullet) {
        return new CollisionResult(Collections.emptySet(), Set.of(bullet), 0, 0);
    }
}
