package starships.colideables;

public class IdVisitor implements Visitor<String> {
    @Override
    public String visitStarship(Starship starship) {
        return "starship-" + starship.getId().toString();
    }

    @Override
    public String visitAsteroid(Asteroid asteroid) {
        return "asteroid-" + asteroid.getId().toString();
    }

    @Override
    public String visitBullet(Bullet bullet) {
        return "bullet-" + bullet.getId().toString();
    }

    @Override
    public String visitPowerUp(PowerUp powerUp) {
        return "powerUp-" + powerUp.getId().toString();
    }
}
