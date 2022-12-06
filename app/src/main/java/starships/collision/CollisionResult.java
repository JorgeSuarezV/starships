package starships.collision;

import java.util.Set;

public class CollisionResult {

    private final Set<Collideable> collideablesToUpdate;
    private final Set<Collideable> collideablesToRemove;
    private final Integer points;
    private final Integer playerNumber;

    public CollisionResult(Set<Collideable> collideablesToUpdate, Set<Collideable> collideablesToRemove, Integer points, Integer playerNumber) {
        this.collideablesToUpdate = collideablesToUpdate;
        this.collideablesToRemove = collideablesToRemove;
        this.points = points;
        this.playerNumber = playerNumber;
    }

    public Set<Collideable> getCollideablesToAdd() {
        return collideablesToUpdate;
    }

    public Integer getPoints() {
        return points;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Set<Collideable> getCollideablesToRemove() {
        return collideablesToRemove;
    }
}
