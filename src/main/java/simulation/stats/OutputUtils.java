package simulation.stats;

public class OutputUtils {
    public static double toTwoDecimalPlaces(double v) {
        return ((Long) Math.round(v * 100)).doubleValue() / 100;
    }
}