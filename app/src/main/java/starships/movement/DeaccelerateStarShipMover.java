package starships.movement;

import starships.keys.KeyService;

import static starships.config.Constants.STARSHIP_SPEED_INCREMENT;

public class DeaccelerateStarShipMover implements Mover {


    @Override
    public MovementData move(Double secondsSinceLastTime, KeyService keyService, Integer playerNumber, MovementData movementData) {
        return deaccelerate(movementData, secondsSinceLastTime);
    }

    private MovementData deaccelerate(MovementData movementData, Double secondsSinceLastTime) {
        final Double deltaxSpeed = (STARSHIP_SPEED_INCREMENT * secondsSinceLastTime / 2.5);
        final Double deltaySpeed = (STARSHIP_SPEED_INCREMENT * secondsSinceLastTime / 2.5);

        return new MovementData(
                movementData.getPosition().sum(movementData.getSpeed().multiply(secondsSinceLastTime)),
                new Vector(deaccelerateSpeed(movementData.getxSpeed(), deltaxSpeed), deaccelerateSpeed(movementData.getySpeed(), deltaySpeed)),
                movementData.getRotation()
        );
    }
    private static Double deaccelerateSpeed(Double speed, Double deltaSpeed) {
        if (speed == 0) return 0d;
        if (speed < 0) return speed + deltaSpeed;
        else return speed - deltaSpeed;
    }
}
