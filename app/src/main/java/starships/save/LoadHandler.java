package starships.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import starships.colideables.*;
import starships.collision.Handler;
import starships.state.GameState;
import starships.state.NormalGameState;

import java.io.FileNotFoundException;

import static starships.Util.Files.readFile;
import static starships.config.Constants.SAVE_FILE_DIR;

public class LoadHandler {

    public GameState handle() {
        try {
            String loadGame = readFile(SAVE_FILE_DIR);
            return loadGameState(loadGame);
        } catch (FileNotFoundException e) {
            return new NormalGameState(1);
        }
    }

    private NormalGameState loadGameState(String loadGame) {
        return createGson().fromJson(loadGame, NormalGameState.class);
    }

    @NotNull
    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(NormalGameState.class, new NormalGameStateDesearilizer())
                .registerTypeAdapter(Starship.class, new StarshipDeserializer())
                .registerTypeAdapter(Weapon.class, new WeaponDeserializer())
                .registerTypeAdapter(Asteroid.class, new AsteroidDeserializer())
                .registerTypeAdapter(Bullet.class, new BulletDeserializer())
                .registerTypeAdapter(PowerUp.class, new PowerUpDeserializer())
                .registerTypeAdapter(PowerUpApplier.class, new PowerUpApplierDeserializer())
                .create();
    }
}
