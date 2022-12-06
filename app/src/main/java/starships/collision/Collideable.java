package starships.collision;

import starships.colideables.Visitable;
import starships.colideables.Visitor;

import java.util.UUID;

public interface Collideable extends Visitable {

    Visitor<CollisionResult> getCollisionVisitor();

    UUID getId();
}
