package starships.save;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import starships.colideables.ClassicWeapon;
import starships.colideables.DoubleCanonWeapon;
import starships.colideables.PowerUpApplier;
import starships.colideables.Weapon;
import starships.colideables.power_up_apliers.DoubleCanonPowerUp;
import starships.colideables.power_up_apliers.InvulnerabilityPowerUp;

import java.lang.reflect.Type;

public class PowerUpApplierSerializer implements JsonSerializer<PowerUpApplier> {
    @Override
    public JsonElement serialize(PowerUpApplier src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", getType(src));
        jsonObject.add("powerUpApplier", context.serialize(src));
        return jsonObject;
    }

    private String getType(PowerUpApplier src) {
        if (src instanceof InvulnerabilityPowerUp) return "invulnerability";
        if (src instanceof DoubleCanonPowerUp) return "double_canon";
        return "invulnerability";
    }
}
