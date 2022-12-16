package starships.save;

import com.google.gson.*;
import org.jetbrains.annotations.NotNull;
import starships.colideables.Asteroid;
import starships.colideables.Bullet;
import starships.colideables.PowerUp;
import starships.colideables.Starship;
import starships.collision.Collideable;
import starships.keys.KeyService;
import starships.state.CollideableMap;
import starships.state.NormalGameState;
import starships.state.ScoreService;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class NormalGameStateDesearilizer implements JsonDeserializer<NormalGameState> {
    @Override
    public NormalGameState deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject asJsonObject = json.getAsJsonObject();
        Set<Map.Entry<String, JsonElement>> activeCollideablesJson = asJsonObject.get("collideableMap").getAsJsonObject().get("activeCollideables").getAsJsonObject().entrySet();
        ConcurrentHashMap<String, Collideable> activeCollideables = parseActiveCollideables(context, activeCollideablesJson);
        return calculateNormalGameState(context, asJsonObject, activeCollideables);
    }

    @NotNull
    private NormalGameState calculateNormalGameState(JsonDeserializationContext context, JsonObject asJsonObject, ConcurrentHashMap<String, Collideable> activeCollideables) {
        return new NormalGameState(
                context.deserialize(asJsonObject.get("playerScores"), ScoreService.class),
                new CollideableMap(activeCollideables),
                context.deserialize(asJsonObject.get("keyService"), KeyService.class),
                asJsonObject.get("playerQuantity").getAsInt()
        );
    }

    @NotNull
    private ConcurrentHashMap<String, Collideable> parseActiveCollideables(JsonDeserializationContext context, Set<Map.Entry<String, JsonElement>> activeCollideables) {
        ConcurrentHashMap<String, Collideable> collideableMap = new ConcurrentHashMap<>();
        for (Map.Entry<String, JsonElement> activeCollideable : activeCollideables) {
            Collideable collideable = parseCollideable(activeCollideable, context);
            if (collideable == null) continue;
            collideableMap.put(collideable.getId(), collideable);
        }
        return collideableMap;
    }

    private Collideable parseCollideable(Map.Entry<String, JsonElement> activeCollideable, JsonDeserializationContext context) {
        if (activeCollideable.getKey().startsWith("starship"))
            return context.deserialize(activeCollideable.getValue(), Starship.class);
        if (activeCollideable.getKey().startsWith("asteroid"))
            return context.deserialize(activeCollideable.getValue(), Asteroid.class);
        if (activeCollideable.getKey().startsWith("bullet"))
            return context.deserialize(activeCollideable.getValue(), Bullet.class);
        if (activeCollideable.getKey().startsWith("powerUp"))
            return context.deserialize(activeCollideable.getValue(), PowerUp.class);
        return null;
    }
}
