package starships.save;

import com.google.gson.*;
import starships.colideables.ClassicWeapon;
import starships.colideables.DoubleCanonWeapon;
import starships.colideables.Weapon;

import java.lang.reflect.Type;

public class WeaponDeserializer implements JsonDeserializer<Weapon> {
    @Override
    public Weapon deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        String type = jsonObject.get("type").getAsString();
        return switch (type) {
            case "classic" -> new ClassicWeapon(jsonObject.get("weapon").getAsJsonObject().get("secondsSinceLastShot").getAsDouble());
            case "double" -> new DoubleCanonWeapon(jsonObject.get("weapon").getAsJsonObject().get("secondsSinceLastShot").getAsDouble());
            default -> new ClassicWeapon(0d);
        };
    }
}
