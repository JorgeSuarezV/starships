package starships.colideables;

public interface Visitor<T> {

    T visitStarship(Starship starship);

    T visitAsteroid(Asteroid asteroid);

    T visitBullet(Bullet bullet);

    T visitPowerUp(PowerUp powerUp);

}
