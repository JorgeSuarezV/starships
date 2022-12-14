package starships.movement;

import starships.keys.KeyService;

public class RotationMover implements Mover {

    private final Boolean isRotatingRigth;

    public RotationMover(Boolean isRotatingRigth) {
        this.isRotatingRigth = isRotatingRigth;
    }

    @Override
    public MovementData move(Double secondsSinceLastTime, KeyService keyService, Integer playerNumber, MovementData movementData) {
        return new MovementData(
                movementData.getPosition(),
                movementData.getSpeed(),
                calculateRotation(movementData, secondsSinceLastTime)
        );
    }


    private Rotation calculateRotation(MovementData movementData, Double secondsSinceLastTime) {
        if (isRotatingRigth)
            return new Rotation(movementData.getAngleInDegrees() + movementData.getRotationInDegrees() * secondsSinceLastTime, movementData.getRotationInDegrees());
        return new Rotation(movementData.getAngleInDegrees() - movementData.getRotationInDegrees() * secondsSinceLastTime, movementData.getRotationInDegrees());
    }
}
