package starships.colideables;

public abstract class AbstractPowerUpApplier implements PowerUpApplier {


    public boolean equals(Object object) {
        if (object == null) return false;
        return object.getClass() == this.getClass();
    }

    public int hashCode() {
        return 789456;
    }
}
