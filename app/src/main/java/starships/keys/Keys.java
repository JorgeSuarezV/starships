package starships.keys;

public class Keys {

    private final String UP;
    private final String LEFT;
    private final String RIGHT;

    Keys(String up, String left, String right) {
        UP = up;
        LEFT = left;
        RIGHT = right;
    }

    public String getUP() {
        return UP;
    }

    public String getLEFT() {
        return LEFT;
    }

    public String getRIGHT() {
        return RIGHT;
    }
}
