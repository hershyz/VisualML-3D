import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class visualml3d {

    public static String filepath;
    public static double[] xVals;
    public static double[] yVals;
    public static double[] zVals;
    public static List<String> lines = new ArrayList<>();

    //Min and max coordinate values align with the index of the unique category in lists:
    public static List<String> uniqueCategories = new ArrayList<>();
    public static List<Double> maxX = new ArrayList<>();
    public static List<Double> maxY = new ArrayList<>();
    public static List<Double> maxZ = new ArrayList<>();
    public static List<Double> minX = new ArrayList<>();
    public static List<Double> minY = new ArrayList<>();
    public static List<Double> minZ = new ArrayList<>();

    //Constructor:
    public visualml3d(String _filepath) {
        filepath = _filepath;
    }

    //Loads all needed values into arrays and lists needed for further calculation:
    public static void init() {

        Path path = Paths.get(filepath);

        try {

            //Sets sizes of coordinate arrays:
            lines = Files.readAllLines(path);
            xVals = new double[lines.size()];
            yVals = new double[lines.size()];
            zVals = new double[lines.size()];

            //Loads coordinate values into arrays and establishes list of unique categories:
            int i;
            for (i = 0; i < lines.size(); i++) {

                String[] lSplit = lines.get(i).split(", ");
                xVals[i] = Double.valueOf(lSplit[0]);
                yVals[i] = Double.valueOf(lSplit[1]);
                zVals[i] = Double.valueOf(lSplit[2]);

                String currentCategory = lSplit[3];
                if (!uniqueCategories.contains(currentCategory)) {
                    uniqueCategories.add(currentCategory);
                }
            }

            //Finds the min and max coordinate values in each dimension for each unique category:
            int j;
            for (j = 0; j < uniqueCategories.size(); j++) {

                String currentCategory = uniqueCategories.get(j);
                double currentMaxX = Double.MIN_VALUE;
                double currentMinX = Double.MAX_VALUE;
                double currentMaxY = Double.MIN_VALUE;
                double currentMinY = Double.MAX_VALUE;
                double currentMaxZ = Double.MIN_VALUE;
                double currentMinZ = Double.MAX_VALUE;

                int k;
                for (k = 0; k < lines.size(); k++) {

                    if (lines.get(k).split(", ")[3].equals(currentCategory)) {

                        String currentLine = lines.get(k);
                        double currentX = Double.valueOf(currentLine.split(", ")[0]);
                        double currentY = Double.valueOf(currentLine.split(", ")[1]);
                        double currentZ = Double.valueOf(currentLine.split(", ")[2]);

                        if (currentX > currentMaxX) {
                            currentMaxX = currentX;
                        }

                        if (currentX < currentMinX) {
                            currentMinX = currentX;
                        }

                        if (currentY > currentMaxY) {
                            currentMaxY = currentY;
                        }

                        if (currentY < currentMinY) {
                            currentMinY = currentY;
                        }

                        if (currentZ > currentMaxZ) {
                            currentMaxZ = currentZ;
                        }

                        if (currentZ < currentMinZ) {
                            currentMinZ = currentZ;
                        }
                    }
                }

                maxX.add(currentMaxX);
                maxY.add(currentMaxY);
                maxZ.add(currentMaxZ);
                minX.add(currentMinX);
                minY.add(currentMinY);
                minZ.add(currentMinZ);
            }
        }

        catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Debug tool to show the summarized important values found during initialization:
    public static void printSummary() {

        //Prints unique categories:
        System.out.print("unique categories found: ");
        String temp = "";

        int i;
        for (i = 0; i < uniqueCategories.size(); i++) {

            temp = temp + uniqueCategories.get(i);
            if (i != uniqueCategories.size() - 1) {
                temp = temp + ", ";
            }
        }

        System.out.println(temp);
        System.out.println("---------------------------");

        //Prints min and max coordinate values for each category:
        int j;
        for (j = 0; j < uniqueCategories.size(); j++) {
            System.out.println("category - " + uniqueCategories.get(j));
            System.out.println("x range: (" + minX.get(j) + ", " + maxX.get(j) + ")");
            System.out.println("y range: (" + minY.get(j) + ", " + maxY.get(j) + ")");
            System.out.println("z range: (" + minZ.get(j) + ", " + maxZ.get(j) + ")");
            System.out.println("---------------------------");
        }
    }

    //Finds the distance between two (x, y, z) coordinates:
    private static double getDistance(double x1, double y1, double z1, double x2, double y2, double z2) {

        double xDifference = x2 - x1;
        double yDifference = y2 - y1;
        double zDifference = z2 - z1;

        xDifference = Math.pow(xDifference, 2);
        yDifference = Math.pow(yDifference, 2);
        zDifference = Math.pow(zDifference, 2);

        double total = xDifference + yDifference + zDifference;
        double result = Math.sqrt(total);

        return result;
    }

    //Used to make a prediction on a coordinate:
    public static String predictCategory(double x, double y, double z) {

        double minDistance = Double.MAX_VALUE;
        String predictedCategory = "";

        int i;
        for (i = 0; i < uniqueCategories.size(); i++) {

            //Finds center coordinate for the current index of unique categories:
            double centerX = (minX.get(i) + maxX.get(i)) / 2;
            double centerY = (minY.get(i) + maxY.get(i)) / 2;
            double centerZ = (minZ.get(i) + maxZ.get(i)) / 2;

            double currentDistance = getDistance(x, y, z, centerX, centerY, centerZ);

            if (currentDistance < minDistance) {
                minDistance = currentDistance;
                predictedCategory = uniqueCategories.get(i);
            }
        }

        return predictedCategory;
    }
}
