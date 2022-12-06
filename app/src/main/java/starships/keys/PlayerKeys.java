package starships.keys;

import java.util.List;

public class PlayerKeys {

    private List<Keys> KEYBINDS;

    public PlayerKeys(List<Keys> keybinds) {
        KEYBINDS = keybinds;
    }

    public Keys getKey(Integer playerNumber){
        return KEYBINDS.get(playerNumber - 1);

    }

    public List<Keys> getKEYBINDS() {
        return KEYBINDS;
    }

    public void setKEYBINDS(List<Keys> KEYBINDS) {
        this.KEYBINDS = KEYBINDS;
    }
}

