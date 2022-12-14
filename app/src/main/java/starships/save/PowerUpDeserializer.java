package starships.save;

import com.google.gson.*;
import starships.colideables.PowerUp;
import starships.colideables.PowerUpApplier;
import starships.collision.ClassicPowerUpCollisionVisitor;
import starships.movement.MovementData;

import java.lang.reflect.Type;

public class PowerUpDeserializer implements JsonDeserializer<PowerUp> {
    @Override
    public PowerUp deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        PowerUpApplier powerUpApplier = context.deserialize(jsonObject.get("powerUpApplier"), PowerUpApplier.class);
        return new PowerUp(
                jsonObject.get("id").getAsString(),
                context.deserialize(jsonObject.get("movementData"), MovementData.class),
                powerUpApplier,
                new ClassicPowerUpCollisionVisitor(powerUpApplier)
        );
    }
}
