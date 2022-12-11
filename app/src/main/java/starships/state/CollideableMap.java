package starships.state;

import starships.collision.Collideable;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static starships.Util.GeneralUtils.toId;

public class CollideableMap {

    private ConcurrentHashMap<String, Collideable> activeCollideables = new ConcurrentHashMap<>();

    public CollideableMap() {
    }

    public CollideableMap(ConcurrentHashMap<String, Collideable> activeCollideables) {
        this.activeCollideables = activeCollideables;
    }

    public CollideableMap addCollideables(Set<Collideable> collideables) {
        ConcurrentHashMap<String, Collideable> newMap = new ConcurrentHashMap<>(activeCollideables);
        for (Collideable collideable : collideables) {
            newMap.put(toId(collideable), collideable);
        }
        return new CollideableMap(newMap);
    }

    public CollideableMap removeCollideables(Set<Collideable> collideables) {
        ConcurrentHashMap<String, Collideable> newMap = new ConcurrentHashMap<>(activeCollideables);
        for (Collideable collideable : collideables) {
            newMap.remove(toId(collideable));
        }
        return new CollideableMap(newMap);
    }

    public CollideableMap removeCollideablesByIds(Set<String> ids) {
        ConcurrentHashMap<String, Collideable> newMap = new ConcurrentHashMap<>(activeCollideables);
        for (String collideable : ids) {
            newMap.remove(collideable);
        }
        return new CollideableMap(newMap);
    }

    public Collideable getColideable(String id) {
        return getAcualCollideablesMap().get(id);
    }

    public Map<String, Collideable> getAcualCollideablesMap() {
        return activeCollideables;
    }

}

