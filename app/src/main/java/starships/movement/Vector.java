package starships.movement;

public class Vector {
    private final Double x;
    private final Double y;

    public Vector(Double x, Double y) {
        this.x = x;
        this.y = y;
    }

    // Constructor for a vector with degrees from X axis
    public Vector(Double degrees, Double module, Integer ignored) {
        this.x = -Math.sin(Math.toRadians(degrees)) * module;
        this.y = Math.cos(Math.toRadians(degrees)) * module;
    }

    public Vector sum(Vector v) {
        final Double newVectorX = this.x + v.getX();
        final Double newVectorY = this.y + v.getY();
        return new Vector(newVectorX, newVectorY);
    }

    public Vector multiply(Double coefficient) {
        return new Vector(this.x * coefficient, this.y * coefficient);
    }

    public Double getX() {
        return x;
    }

    public Double getY() {
        return y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vector vector = (Vector) o;
        return Math.abs(this.x - vector.getX()) < 0.05 && Math.abs(this.y - vector.getY()) < 0.05;
    }

    @Override
    public int hashCode() {
        return 54564654;
    }
}

