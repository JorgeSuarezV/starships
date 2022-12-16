package starships.Util;

import java.util.HashSet;

public class NoNullSet<T> extends HashSet<T> {

    public boolean add(T t) {
        if (t == null) return true;
        return super.add(t);
    }
}
