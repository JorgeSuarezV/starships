package starships.Util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Files {

    public static String readFile(String filename) throws FileNotFoundException {
        return new Scanner(new File(filename)).useDelimiter("\\Z").next();
    }
}
