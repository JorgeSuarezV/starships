package starships.colideables;

public class BulletData {
    private final Integer playerNumber;
    private final Double pointMultiplier;
    private final Double damage;

    public BulletData(Integer playerNumber, Double pointMultiplier, Double damage) {
        this.playerNumber = playerNumber;
        this.pointMultiplier = pointMultiplier;
        this.damage = damage;
    }

    public Double getPointMultiplier() {
        return pointMultiplier;
    }

    public Integer getPlayerNumber() {
        return playerNumber;
    }

    public Double getDamage() {
        return damage;
    }
}