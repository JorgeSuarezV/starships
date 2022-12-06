package starships.colideables;

import starships.collision.Collideable;

import java.util.Set;

public class ShootResult {

    private final Weapon weapon;
    private final Set<Collideable> newBullets;

    public ShootResult(Weapon weapon, Set<Collideable> newBullets) {
        this.weapon = weapon;
        this.newBullets = newBullets;
    }

    public Weapon getWeapon() {
        return weapon;
    }

    public Set<Collideable> getNewBullets() {
        return newBullets;
    }
}
