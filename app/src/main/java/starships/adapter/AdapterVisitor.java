package starships.adapter;

import edu.austral.ingsis.starships.ui.ElementModel;
import edu.austral.ingsis.starships.ui.ImageRef;
import starships.colideables.*;
import starships.colideables.power_up_apliers.DoubleCanonPowerUp;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;

import static starships.Util.GeneralUtils.toId;
import static starships.config.Constants.*;


public class AdapterVisitor implements Visitor<ElementModel> {


    @Override
    public ElementModel visitStarship(Starship starship) {
        return new ElementModel(toId(starship),
                starship.getMovementData().getxPosition(),
                starship.getMovementData().getyPosition(),
                50d,
                50d,
                starship.getMovementData().getAngleInDegrees(),
                SHIP_COLLIDER_TYPE,
                STARSHIP_IMAGE_REF
        );
    }

    @Override
    public ElementModel visitAsteroid(Asteroid asteroid) {
        return new ElementModel(
                toId(asteroid),
                asteroid.getMovementData().getxPosition(),
                asteroid.getMovementData().getyPosition(),
                asteroid.getHealth() / 2 + 80d,
                asteroid.getHealth() / 2 + 40d,
                asteroid.getMovementData().getAngleInDegrees(),
                ASTEROID_COLLIDER_TYPE,
                calculateImage(asteroid.getHealth())
        );
    }

    private ImageRef calculateImage(Double asteroidHealth) {
        if (asteroidHealth > 100) return ASTEROID_IMAGE_REF_1;
        if (asteroidHealth > 74) return ASTEROID_IMAGE_REF_2;
        return ASTEROID_IMAGE_REF_3;
    }

    @Override
    public ElementModel visitBullet(Bullet bullet) {
        return new ElementModel(
                toId(bullet),
                bullet.getMovementData().getxPosition(),
                bullet.getMovementData().getyPosition(),
                BULLET_HEIGHT,
                BULLET_WIDTH,
                bullet.getMovementData().getAngleInDegrees(),
                BULLET_COLLIDER_TYPE,
                BULLET_IMAGE_REF
        );
    }

    @Override
    public ElementModel visitPowerUp(PowerUp powerUp) {
        return new ElementModel(
                toId(powerUp),
                powerUp.getMovementData().getxPosition(),
                powerUp.getMovementData().getyPosition(),
                50d,
                50d,
                powerUp.getMovementData().getAngleInDegrees(),
                POWER_UP_COLLIDER_TYPE,
                grabImageRef(powerUp)
        );
    }

    private ImageRef grabImageRef(PowerUp powerUp) {
        if (powerUp.getPowerUpAplier().getClass() == DoubleCanonPowerUp.class) return DOUBLE_CANON_IMAGE_RED;
        if (powerUp.getPowerUpAplier().getClass() == InvulnerabilityPowerUp.class) return INVULNERABILITY_IMAGE_RED;
        else return null;
    }
}
