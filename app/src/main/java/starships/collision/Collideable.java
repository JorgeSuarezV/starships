package starships.collision;

import starships.colideables.Visitable;
import starships.colideables.Visitor;
import starships.keys.KeyService;

import java.util.UUID;

public interface Collideable extends Visitable {

    Visitor<CollisionResult> getCollisionVisitor();

    Collideable move(Double secondsSinceLastTime, KeyService keyService);

    UUID getId();
}
