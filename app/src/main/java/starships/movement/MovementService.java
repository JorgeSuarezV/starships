package starships.movement;

public class MovementService {

    public static MovementData noAccelerationNewMovement(MovementData actualMovement, Double secondsSinceLastTime) {
        return new MovementData(
                actualMovement.getPosition().sum(actualMovement.getSpeed().multiply(secondsSinceLastTime)),
                actualMovement.getSpeed(),
                new Rotation(actualMovement.getAngleInDegrees() + actualMovement.getRotationInDegrees(), actualMovement.getRotationInDegrees())
        );
    }
}
