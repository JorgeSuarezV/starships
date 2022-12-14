package starships.spawn;

import starships.colideables.ClassicWeapon;
import starships.colideables.Starship;
import starships.collision.ClassicStarshipCollisionVisitor;
import starships.collision.Collideable;
import starships.movement.MovementData;
import starships.movement.Rotation;
import starships.movement.StarshipMover;
import starships.movement.Vector;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import static starships.config.Constants.*;

public class StarshipInitializer {


    public Set<Collideable> spawnStarships(Integer playerNumber) {
        Set<Collideable> starships = new HashSet<>();
        addShipsToSet(playerNumber, starships);
        return starships;
    }

    private void addShipsToSet(Integer playerNumber, Set<Collideable> starships) {
        for (int i = 1; i <= playerNumber; i++) {
            Starship starship = createStarship(i);
            starships.add(starship);
        }
    }

    private Vector startPostion(Integer playerNumber) {
        return switch (playerNumber) {
            case 1 -> new Vector(100d, 100d);
            case 2 -> new Vector(GAME_WIDTH - 100d, 100d);
            case 3 -> new Vector(100d, GAME_HEIGHT - 100d);
            case 4 -> new Vector(GAME_WIDTH - 100d, GAME_HEIGHT - 100d);
            default -> throw new IndexOutOfBoundsException("Player quantity not supported");
        };
    }

    private Starship createStarship(Integer playerNumber) {
        return new Starship(
                playerNumber,
                Set.of(),
                new MovementData(
                        startPostion(playerNumber),
                        new Vector(0d, 0d),
                        new Rotation(startAngle(playerNumber), STARSHIP_ROTATION_DEGREES)
                ),
                LIVES,
                new ClassicWeapon(0d),
                new ClassicStarshipCollisionVisitor(playerNumber),
                new StarshipMover());
    }

    private Double startAngle(Integer playerNumber) {
        if (playerNumber % 2 == 1) return -STARTING_ANGLE;
        return STARTING_ANGLE;
    }
}
