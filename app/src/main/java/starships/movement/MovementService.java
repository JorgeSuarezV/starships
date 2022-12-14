package starships.movement;

import static starships.config.Constants.GAME_HEIGHT;
import static starships.config.Constants.GAME_WIDTH;

public class MovementService {

    public static MovementData noAccelerationNewMovement(MovementData actualMovement, Double secondsSinceLastTime) {
        return new MovementData(
                actualMovement.getPosition().sum(actualMovement.getSpeed().multiply(secondsSinceLastTime)),
                actualMovement.getSpeed(),
                new Rotation(actualMovement.getAngleInDegrees() + actualMovement.getRotationInDegrees(), actualMovement.getRotationInDegrees())
        );
    }

    public static boolean isOutOfBounds(MovementData movementData) {
        Vector position = movementData.getPosition();
        return position.getX() < 0 || position.getX() > GAME_WIDTH || position.getY() < 0 || position.getY() > GAME_HEIGHT;
    }
}
