package starships.save;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.jetbrains.annotations.NotNull;
import starships.colideables.PowerUpApplier;
import starships.colideables.Weapon;
import starships.state.GameState;
import starships.state.PausedGameState;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;

import static starships.config.Constants.SAVE_DIR;
import static starships.config.Constants.SAVE_FILE_DIR;

public class SaveHandler {

    public GameState handle(GameState gameState) {
        try {
            saveFile(gameState);
            return new PausedGameState(gameState);
        } catch (IOException e) {
            return gameState;
        }
    }

    private void saveFile(GameState gameState) throws IOException {
        Files.createDirectories(new File(SAVE_DIR).toPath());
        BufferedWriter writer = new BufferedWriter(new FileWriter(SAVE_FILE_DIR));
        Gson gson = createGson();
        writer.write(gson.toJson(gameState));
        writer.close();
    }

    @NotNull
    private Gson createGson() {
        return new GsonBuilder()
                .registerTypeAdapter(Weapon.class, new WeaponSerializer())
                .registerTypeAdapter(PowerUpApplier.class, new PowerUpApplierSerializer())
                .create();
    }
}
