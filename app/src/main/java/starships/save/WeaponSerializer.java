package starships.save;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import starships.colideables.ClassicWeapon;
import starships.colideables.DoubleCanonWeapon;
import starships.colideables.Weapon;

import java.lang.reflect.Type;

public class WeaponSerializer implements JsonSerializer<Weapon> {
    @Override
    public JsonElement serialize(Weapon src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("type", getType(src));
        jsonObject.add("weapon", context.serialize(src));
        return jsonObject;
    }

    private String getType(Weapon src) {
        if (src instanceof ClassicWeapon) return "classic";
        if (src instanceof DoubleCanonWeapon) return "double";
        return "classic";
    }
}
