package starships.save;

import com.google.gson.*;
import starships.colideables.Bullet;
import starships.colideables.BulletData;
import starships.collision.ClassicBulletBehavior;
import starships.collision.ClassicBulletCollisionVisitor;
import starships.movement.MovementData;

import java.lang.reflect.Type;

public class BulletDeserializer implements JsonDeserializer<Bullet> {
    @Override
    public Bullet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jsonObject = json.getAsJsonObject();
        BulletData bulletData = context.deserialize(jsonObject.get("bulletData"), BulletData.class);
        return new Bullet(
                jsonObject.get("id").getAsString(),
                context.deserialize(jsonObject.get("movementData"), MovementData.class),
                bulletData,
                new ClassicBulletBehavior(),
                new ClassicBulletCollisionVisitor(bulletData)
        );
    }
}
