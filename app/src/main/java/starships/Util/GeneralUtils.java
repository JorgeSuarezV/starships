package starships.Util;

import java.util.Random;

public class GeneralUtils {


    public static double getRandomValue(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

}
