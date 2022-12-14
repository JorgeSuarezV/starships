package starships.config;

import edu.austral.ingsis.starships.ui.ElementColliderType;
import edu.austral.ingsis.starships.ui.ImageRef;

public class Constants {

    //GAME CONSTANTS
    public static final double GAME_WIDTH = 1000;
    public static final double GAME_HEIGHT = 700;
    public static final String SAVE_DIR = System.getenv("PROGRAMFILES") + "/starship/saves";
    public static final String SAVE_FILE_DIR = SAVE_DIR + "/save1.json";


    //SHIP CONSTANTS
    public static final int LIVES = 5;
    public static final double STARSHIP_SPEED_INCREMENT = 300d;
    public static final double STARSHIP_ROTATION_DEGREES = 180d;
    public static final double STARTING_ANGLE = 90;

    //BULLET CONSTANTS
    public static final double BULLET_SPEED = 400d;
    public static final int BULLET_HEIGHT = 30;
    public static final int BULLET_WIDTH = 20;
    public static final double CLASSIC_DAMAGE = 10;
    public static final double DOUBLE_DAMAGE = 10;
    public static final double CLASSIC_WEAPON_FIRE_RATE = 0.2d;
    public static final double DOUBLE_WEAPON_FIRE_RATE = 0.2d;


    //ASTEROID CONSTANTS
    public static final int MAX_ASTEROID_SIZE = 130;
    public static final int MIN_ASTEROID_SIZE = 50;

    public static final double ASTEROID_SPEED_COEFFICIENT = 0.3;

    //KEY CONSTANTS
    public static final String PAUSE_GAME_KEY = "P";
    public static final String SAVE_GAME_KEY = "ENTER";
    public static final String LOAD_GAME_KEY = "BACK_SPACE";


    //IMAGE REFERENCES

    public static final ImageRef STARSHIP_IMAGE_REF = new ImageRef("ship", 150d, 150d);
    public static final ImageRef BULLET_IMAGE_REF = new ImageRef("classic_dart", 60d, 60d);
    public static final ImageRef DOUBLE_CANON_IMAGE_RED = new ImageRef("double_canon", 60d, 60d);
    public static final ImageRef INVULNERABILITY_IMAGE_RED = new ImageRef("invulnerability", 60d, 60d);
    public static final ImageRef ASTEROID_IMAGE_REF_1 = new ImageRef("yellow_bloon", 300d, 150d);
    public static final ImageRef ASTEROID_IMAGE_REF_2 = new ImageRef("blue_bloon", 300d, 150d);
    public static final ImageRef ASTEROID_IMAGE_REF_3 = new ImageRef("red_bloon", 300d, 150d);
    public static final ElementColliderType ASTEROID_COLLIDER_TYPE = ElementColliderType.Elliptical;
    public static final ElementColliderType SHIP_COLLIDER_TYPE = ElementColliderType.Elliptical;
    public static final ElementColliderType BULLET_COLLIDER_TYPE = ElementColliderType.Rectangular;
    public static final ElementColliderType POWER_UP_COLLIDER_TYPE = ElementColliderType.Rectangular;
}
