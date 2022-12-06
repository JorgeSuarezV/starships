package starships.colideables;

public interface Visitable {

    <T> T accept(Visitor<T> visitor);
}
