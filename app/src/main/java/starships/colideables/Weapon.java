package starships.colideables;

public interface Weapon {

    ShootResult shoot(Starship origin, Double secondsSinceLastTime);
}
