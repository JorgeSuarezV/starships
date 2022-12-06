package starships.colideables.power_up_apliers;

import org.jetbrains.annotations.Nullable;
import starships.colideables.ClassicWeapon;
import starships.colideables.DoubleCanonWeapon;
import starships.colideables.PowerUpApplier;
import starships.colideables.Starship;

import java.util.HashSet;
import java.util.Set;

import static starships.Util.Sets.mergeSetWithItem;

public class DoubleCanonPowerUp implements PowerUpApplier {

    private final Double duration;
    private final Double timeSinceStart;

    public DoubleCanonPowerUp(Double duration, Double timeSinceStart) {
        this.duration = duration;
        this.timeSinceStart = timeSinceStart;
    }


    @Override
    public Starship mount(Starship starship) {
        return new Starship(
                starship.getId(),
                starship.getPlayerNumber(),
                mergeSetWithItem(starship.getStarshipPowerUps(), this),
                starship.getMovementData(),
                starship.getLives(),
                new DoubleCanonWeapon(0d),
                starship.getCollisionVisitor()
        );
    }

    @Override
    public Starship dismount(Starship starship) {
        return new Starship(
                starship.getId(),
                starship.getPlayerNumber(),
                removePowerUp(starship.getStarshipPowerUps()),
                starship.getMovementData(),
                starship.getLives(),
                new ClassicWeapon(0d),
                starship.getCollisionVisitor()
        );
    }

    private Set<PowerUpApplier> removePowerUp(Set<PowerUpApplier> starshipPowerUps) {
        Set<PowerUpApplier> newPowerUps = new HashSet<>(starshipPowerUps);
        newPowerUps.remove(this);
        return newPowerUps;
    }

    @Override
    public boolean isOver(Double secondsSinceLastTime) {
        return timeSinceStart + secondsSinceLastTime >= duration;
    }

    @Nullable
    @Override
    public PowerUpApplier addTime(Double secondsSinceLastTime) {
        return new DoubleCanonPowerUp(duration, timeSinceStart + secondsSinceLastTime);
    }
}
