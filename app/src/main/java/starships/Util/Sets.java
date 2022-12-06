package starships.Util;

import java.util.HashSet;
import java.util.Set;

public class Sets {

    public static <T> Set<T> mergeSet(Set<T> objectSet1, Set<T> objectSet2) {
        Set<T> objects = new HashSet<>();
        objects.addAll(objectSet1);
        objects.addAll(objectSet2);
        return objects;
    }

    public static <T> Set<T> mergeSet(Set<T> objectSet1, Set<T> objectSet2, Set<T> objectSet3) {
        Set<T> objects = new HashSet<>();
        objects.addAll(objectSet1);
        objects.addAll(objectSet2);
        objects.addAll(objectSet3);
        return objects;
    }

    public static <T> Set<T> mergeSetWithItem(Set<T> objectSet1, T object2) {
        Set<T> objects = new HashSet<>(objectSet1);
        objects.add(object2);
        return objects;
    }
}
