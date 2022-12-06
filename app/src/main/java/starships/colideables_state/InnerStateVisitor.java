package starships.colideables_state;

import starships.colideables.*;
import starships.collision.Collideable;
import starships.keys.KeyService;
import starships.movement.MovementData;
import starships.movement.MovementVisitor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static starships.Util.Sets.mergeSetWithItem;

public class InnerStateVisitor implements Visitor<Set<Collideable>> {

    private final Double secondsSinceLastTime;
    private final Visitor<MovementData> movementVisitor;

    public InnerStateVisitor(Double secondsSinceLastTime, KeyService keyService) {
        this.secondsSinceLastTime = secondsSinceLastTime;
        this.movementVisitor  = new MovementVisitor(secondsSinceLastTime, keyService);
    }

    @Override
    public Set<Collideable> visitStarship(Starship starship) {
        Starship newStarship = calculatePUNewState(starship);
        ShootResult shot = newStarship.getWeapon().shoot(starship, secondsSinceLastTime);
        Collideable shotStarship = updateStarshipAfterShot(newStarship, shot);
        return mergeSetWithItem(shot.getNewBullets(), shotStarship);
    }

    private Starship updateStarshipAfterShot(Starship starship, ShootResult shot) {
        return new Starship(
                starship.getId(),
                starship.getPlayerNumber(),
                starship.getStarshipPowerUps(),
                starship.accept(movementVisitor),
                starship.getLives(),
                shot.getWeapon(),
                starship.getCollisionVisitor()
        );
    }

    private Starship calculatePUNewState(Starship starship) {
        return updatePowerUps(starship, new ArrayList<>(starship.getStarshipPowerUps()), new HashSet<>(), 0);
    }

    private Starship updatePowerUps(Starship starship, List<PowerUpApplier> oldPU, Set<PowerUpApplier> newSet, Integer start) {
        for (int i = start; i < oldPU.size(); i++) {
            if (oldPU.get(i).isOver(secondsSinceLastTime)) {
                return updatePowerUps(oldPU.get(i).dismount(starship), oldPU, newSet, ++i);
            }
            else newSet.add(oldPU.get(i).addTime(secondsSinceLastTime));
        }
        return createNewStarship(starship, newSet);
    }

    private Starship createNewStarship(Starship starship, Set<PowerUpApplier> newPUSet) {
        return new Starship(
                starship.getId(),
                starship.getPlayerNumber(),
                newPUSet,
                starship.getMovementData(),
                starship.getLives(),
                starship.getWeapon(),
                starship.getCollisionVisitor()
        );
    }

    @Override
    public Set<Collideable> visitAsteroid(Asteroid asteroid) {
        return Set.of(new Asteroid(
                asteroid.getId(),
                asteroid.accept(movementVisitor),
                asteroid.getHealth(),
                asteroid.getPoints(),
                asteroid.getCollisionVisitor()
        ));
    }

    @Override
    public Set<Collideable> visitBullet(Bullet bullet) {
        return Set.of(new Bullet(
                bullet.getId(),
                bullet.accept(movementVisitor),
                bullet.getBulletData(),
                bullet.getBulletBehavior(),
                bullet.getCollisionVisitor()
        ));
    }

    @Override
    public Set<Collideable> visitPowerUp(PowerUp powerUp) {
        return Set.of(new PowerUp(
                powerUp.getId(),
                powerUp.accept(movementVisitor),
                powerUp.getPowerUpAplier(),
                powerUp.getCollisionResultVisitor()
        ));
    }
}
