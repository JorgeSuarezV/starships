package starships.save;

import com.google.gson.*;
import starships.colideables.Asteroid;
import starships.collision.ClassicAsteroidCollisionVisitor;
import starships.movement.MovementData;

import java.lang.reflect.Type;

public class AsteroidDeserializer implements JsonDeserializer<Asteroid> {
    @Override
    public Asteroid deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        return new Asteroid(
                jsonObject.get("id").getAsString(),
                context.deserialize(jsonObject.get("movementData"), MovementData.class),
                jsonObject.get("health").getAsDouble(),
                jsonObject.get("points").getAsInt(),
                new ClassicAsteroidCollisionVisitor()
        );
    }
}
