package starships.movement;

import starships.keys.KeyService;

public interface Mover {

    MovementData move(Double secondsSinceLastTime, KeyService keyService, Integer playerNumber, MovementData movementData);
}
