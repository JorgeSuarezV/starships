package starships.movement;

public class Rotation {

    private final Double angleInDegrees;
    private final Double rotationInDegrees;

    public Rotation(Double angleInDegrees, Double rotationInDegrees) {
        this.angleInDegrees = angleInDegrees;
        this.rotationInDegrees = rotationInDegrees;
    }

    public Double getAngleInDegrees() {
        return angleInDegrees;
    }

    public Double getRotationInDegrees() {
        return rotationInDegrees;
    }
}
