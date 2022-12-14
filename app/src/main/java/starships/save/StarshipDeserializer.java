package starships.save;

import com.google.gson.*;
import starships.colideables.Starship;
import starships.colideables.Weapon;
import starships.collision.ClassicStarshipCollisionVisitor;
import starships.movement.MovementData;
import starships.movement.StarshipMover;

import java.lang.reflect.Type;
import java.util.HashSet;

public class StarshipDeserializer implements JsonDeserializer<Starship> {
    @Override
    public Starship deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject asJsonObject = json.getAsJsonObject();
        return new Starship(
                asJsonObject.get("id").getAsString(),
                asJsonObject.get("playerNumber").getAsInt(),
                new HashSet<>(),
                context.deserialize(asJsonObject.get("movementData"), MovementData.class),
                asJsonObject.get("lives").getAsInt(),
                context.deserialize(asJsonObject.get("weapon"), Weapon.class),
                new ClassicStarshipCollisionVisitor(asJsonObject.get("playerNumber").getAsInt()),
                new StarshipMover()
        );
    }
}
