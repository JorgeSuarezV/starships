package starships.movement;

import starships.keys.KeyService;

import static starships.config.Constants.STARSHIP_SPEED_INCREMENT;

public class AccelerateStarShipMover implements Mover {
    @Override
    public MovementData move(Double secondsSinceLastTime, KeyService keyService, Integer playerNumber, MovementData movementData) {
        return accelerate(movementData, secondsSinceLastTime);
    }

    private MovementData accelerate(MovementData movementData, Double secondsSinceLastTime) {
        final Double xSpeed = Math.cos(Math.toRadians(movementData.getAngleInDegrees() + 90)) * STARSHIP_SPEED_INCREMENT * secondsSinceLastTime + movementData.getxSpeed();
        final Double ySpeed = Math.sin(Math.toRadians(movementData.getAngleInDegrees() + 90)) * STARSHIP_SPEED_INCREMENT * secondsSinceLastTime+ movementData.getySpeed();

        return new MovementData(
                movementData.getPosition().sum(movementData.getSpeed().multiply(secondsSinceLastTime)),
                new Vector(xSpeed, ySpeed),
                movementData.getRotation()
        );
    }
}
