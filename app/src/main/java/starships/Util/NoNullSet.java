package starships.Util;

import starships.collision.Collideable;

import java.util.HashSet;
import java.util.Set;

public class NoNullSet<T> extends HashSet<T> {

    public boolean add(T t) {
        if (t == null) return true;
        return super.add(t);
    }
}
