package starships.movement;

import starships.colideables.*;
import starships.keys.KeyService;
import starships.keys.MovementKeys;

import static starships.config.Constants.*;

public class MovementVisitor implements Visitor<MovementData> {

    private final Double secondsSinceLastTime;
    private final KeyService keyService;

    public MovementVisitor(Double secondsSinceLastTime, KeyService keyService) {
        this.secondsSinceLastTime = secondsSinceLastTime;
        this.keyService = keyService;
    }

    @Override
    public MovementData visitStarship(Starship starship) {
        return calculateNewMovementData(starship);
    }

    private MovementData calculateNewMovementData(Starship starship) {
        if (isAccelerating(starship)) return checkInBounds(accelerateAndRotate(starship.getMovementData(), starship.getPlayerNumber()));
        else return checkInBounds(deaccelerateAndRotate(starship.getMovementData(), starship.getPlayerNumber()));
    }

    private MovementData deaccelerateAndRotate(MovementData movementData, Integer playerNumber) {
        final Double deltaxSpeed = (STARSHIP_SPEED_INCREMENT / 2.5);
        final Double deltaySpeed = (STARSHIP_SPEED_INCREMENT / 2.5);

        return new MovementData(
                movementData.getPosition().sum(movementData.getSpeed().multiply(secondsSinceLastTime)),
                new Vector(deaccelerateSpeed(movementData.getxSpeed(), deltaxSpeed), deaccelerateSpeed(movementData.getySpeed(), deltaySpeed)),
                calculateRotation(movementData, playerNumber)
                );
    }

    private MovementData checkInBounds(MovementData movementData) {
        final Double xPosition = checkInBounds(movementData.getxPosition(), GAME_WIDTH);
        final Double yPosition = checkInBounds(movementData.getyPosition(), GAME_HEIGHT);


        return new MovementData(new Vector(xPosition, yPosition), movementData.getSpeed(), movementData.getRotation());
    }

    private Double checkInBounds(Double position, Double limit) {
        if (position >= limit) {
            return position - limit;
        }else if (position <= 0d){
            return position + limit;
        }else {
            return position;
        }
    }

    private static Double deaccelerateSpeed(Double speed, Double deltaSpeed) {
        if (speed == 0) return 0d;
        if (speed < 0) return speed + deltaSpeed;
        else return speed - deltaSpeed;
    }

    private MovementData accelerateAndRotate(MovementData movementData, Integer playerNumber) {
        final Double xSpeed = Math.cos(Math.toRadians(movementData.getAngleInDegrees() + 90)) * STARSHIP_SPEED_INCREMENT + movementData.getxSpeed();
        final Double ySpeed = Math.sin(Math.toRadians(movementData.getAngleInDegrees() + 90)) * STARSHIP_SPEED_INCREMENT + movementData.getySpeed();

        return new MovementData(
                movementData.getPosition().sum(movementData.getSpeed().multiply(secondsSinceLastTime)),
                new Vector(xSpeed, ySpeed),
                calculateRotation(movementData, playerNumber)
        );
    }

    private Rotation calculateRotation(MovementData movementData, Integer playernumber) {
        if (keyService.isPressed(MovementKeys.LEFT, playernumber)) return new Rotation(movementData.getAngleInDegrees() - movementData.getRotationInDegrees(), movementData.getRotationInDegrees());
        if (keyService.isPressed(MovementKeys.RIGHT, playernumber)) return new Rotation(movementData.getAngleInDegrees() + movementData.getRotationInDegrees(), movementData.getRotationInDegrees());
        return movementData.getRotation();
    }

    private boolean isAccelerating(Starship starship) {
        return keyService.isPressed(MovementKeys.UP, starship.getPlayerNumber());
    }

    @Override
    public MovementData visitAsteroid(Asteroid asteroid) {
        return noAccelerationNewMovement(asteroid.getMovementData(), secondsSinceLastTime);
    }

    @Override
    public MovementData visitBullet(Bullet bullet) {
        return noAccelerationNewMovement(bullet.getMovementData(), secondsSinceLastTime);
    }

    @Override
    public MovementData visitPowerUp(PowerUp powerUp) {
        return noAccelerationNewMovement(powerUp.getMovementData(), secondsSinceLastTime);
    }


    public static MovementData noAccelerationNewMovement(MovementData actualMovement, Double secondsSinceLastTime){
        return new MovementData(
                actualMovement.getPosition().sum(actualMovement.getSpeed().multiply(secondsSinceLastTime)),
                actualMovement.getSpeed(),
                new Rotation(actualMovement.getAngleInDegrees() + actualMovement.getRotationInDegrees(), actualMovement.getRotationInDegrees())
        );
    }


}
