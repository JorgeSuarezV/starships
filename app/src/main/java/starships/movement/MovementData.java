package starships.movement;

public class MovementData {

    private final Vector position;

    private final Vector speed;

    private final Rotation rotation;

    public MovementData(Vector position, Vector speed, Rotation rotation) {
        this.position = position;
        this.speed = speed;
        this.rotation = rotation;
    }


    public Vector getPosition() {
        return position;
    }

    public Vector getSpeed() {
        return speed;
    }

    public Double getxPosition() {
        return position.getX();
    }

    public Double getyPosition() {
        return position.getY();
    }

    public Double getxSpeed() {
        return speed.getX();
    }

    public Double getySpeed() {
        return speed.getY();
    }

    public Double getAngleInDegrees() {
        return rotation.getAngleInDegrees();
    }
    public Double getRotationInDegrees(){
        return rotation.getRotationInDegrees();
    }
    public Rotation getRotation(){
        return rotation;
    }
}
