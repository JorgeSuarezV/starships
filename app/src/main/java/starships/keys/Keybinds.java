package starships.keys;

import com.google.gson.Gson;

import static starships.Util.Files.readFile;

public class Keybinds {


    private static final PlayerKeys KEYBINDS = readKeys();

    public static Keys getKey(Integer playerNumber) {
        assert KEYBINDS != null;
        return KEYBINDS.getKey(playerNumber);
    }


    private static PlayerKeys readKeys() {
        try {
            String config = readFile("keybinds.json");
            return new Gson().fromJson(config, PlayerKeys.class);
        } catch (Exception e) {
            // TODO load defaults
        }
        return null;
    }


}
