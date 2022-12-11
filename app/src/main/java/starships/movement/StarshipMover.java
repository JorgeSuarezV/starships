package starships.movement;

import starships.keys.KeyService;
import starships.keys.MovementKeys;

import java.util.HashMap;
import java.util.Map;

import static starships.config.Constants.GAME_HEIGHT;
import static starships.config.Constants.GAME_WIDTH;

public class StarshipMover implements Mover {

    private final Map<MovementKeys, Mover> movementMap = initializeMovementMap();

    private Map<MovementKeys, Mover> initializeMovementMap() {
        Map<MovementKeys, Mover> newMap = new HashMap<>();
        newMap.put(MovementKeys.UP, new AccelerateStarShipMover());
        newMap.put(MovementKeys.RIGHT, new RotationMover(true));
        newMap.put(MovementKeys.LEFT, new RotationMover(false));
        newMap.put(MovementKeys.NO_UP, new DeaccelerateStarShipMover());
        return newMap;
    }

    @Override
    public MovementData move(Double secondsSinceLastTime, KeyService keyService, Integer playerNumber, MovementData movementData) {
        for (MovementKeys movementKeys : movementMap.keySet()) {
            if (keyService.isPressed(movementKeys, playerNumber))
                movementData = checkInBounds(movementMap.get(movementKeys).move(secondsSinceLastTime, keyService, playerNumber, movementData));
        }
        return movementData;
    }

    private MovementData checkInBounds(MovementData movementData) {
        final Double xPosition = checkInBounds(movementData.getxPosition(), GAME_WIDTH);
        final Double yPosition = checkInBounds(movementData.getyPosition(), GAME_HEIGHT);
        return new MovementData(new Vector(xPosition, yPosition), movementData.getSpeed(), movementData.getRotation());
    }

    private Double checkInBounds(Double position, Double limit) {
        if (position >= limit) {
            return position - limit;
        } else if (position <= 0d) {
            return position + limit;
        } else {
            return position;
        }
    }
}
