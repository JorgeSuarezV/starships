package starships.keys;

import javafx.scene.input.KeyCode;

import java.util.HashSet;
import java.util.Set;

import static starships.keys.Keybinds.getKey;

public class KeyService {

    private final Set<String> pressedKeys;

    public KeyService(Set<String> pressedKeys) {
        this.pressedKeys = pressedKeys;
    }

    public KeyService() {
        this.pressedKeys = new HashSet<>();
    }

    public KeyService keyPressed(KeyCode keyPressed) {
        Set<String> newSet = new HashSet<>(pressedKeys);
        newSet.add(keyPressed.getName().toUpperCase());
        return new KeyService(newSet);
    }

    public KeyService keyReleased(KeyCode keyReleased) {
        Set<String> newSet = new HashSet<>(pressedKeys);
        newSet.remove(keyReleased.getName().toUpperCase());
        return new KeyService(newSet);
    }

    public boolean isPressed(MovementKeys key, Integer playerNumber) {
        Keys keys = getKey(playerNumber);
        return switch (key) {
            case UP -> pressedKeys.contains(keys.getUP());
            case RIGHT -> pressedKeys.contains(keys.getRIGHT());
            case LEFT -> pressedKeys.contains(keys.getLEFT());
            case NO_UP -> !pressedKeys.contains(keys.getUP());
        };
    }
}
