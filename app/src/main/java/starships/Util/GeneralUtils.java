package starships.Util;

import starships.colideables.IdVisitor;
import starships.colideables.Visitor;
import starships.collision.Collideable;

import java.util.Random;

public class GeneralUtils {

    private static final Visitor<String> idVisitor = new IdVisitor();

    public static double getRandomValue(double min, double max) {
        return min + (max - min) * new Random().nextDouble();
    }

    public static String toId(Collideable collideable) {
        return collideable.accept(idVisitor);
    }

}
