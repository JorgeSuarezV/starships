package starships.collision;

import org.jetbrains.annotations.Nullable;
import starships.colideables.Visitable;
import starships.colideables.Visitor;
import starships.keys.KeyService;

public interface Collideable extends Visitable {

    Visitor<CollisionResult> getCollisionVisitor();

    @Nullable
    Collideable move(Double secondsSinceLastTime, KeyService keyService);

    String getId();
}
