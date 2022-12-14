package starships.save;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import starships.colideables.ClassicWeapon;
import starships.colideables.DoubleCanonWeapon;
import starships.colideables.PowerUpApplier;
import starships.colideables.power_up_apliers.DoubleCanonPowerUp;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;

import java.lang.reflect.Type;

public class PowerUpApplierDeserializer implements JsonDeserializer<PowerUpApplier> {
    @Override
    public PowerUpApplier deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        return parsePowerUpApplier(jsonObject, type);
    }

    private PowerUpApplier parsePowerUpApplier(JsonObject jsonObject, String type) {
        JsonObject powerUpApplier = jsonObject.get("powerUpApplier").getAsJsonObject();
        return switch (type) {
            case "invulnerability" -> createInvulnerability(powerUpApplier);
            case "double_canon" -> createDoubleCanon(powerUpApplier);
            default -> null;
        };
    }

    @NotNull
    private DoubleCanonPowerUp createDoubleCanon(JsonObject powerUpApplier) {
        return new DoubleCanonPowerUp(
                powerUpApplier.get("duration").getAsDouble(),
                powerUpApplier.get("timeSinceStart").getAsDouble()
        );
    }

    private PowerUpApplier createInvulnerability(JsonObject powerUpApplier) {
        return new InvulnerabilityPowerUp(
                powerUpApplier.get("duration").getAsDouble(),
                powerUpApplier.get("timeSinceStart").getAsDouble()
        );
    }
}
