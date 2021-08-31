package simulation.stats;

public class DailyStats {
    public final int day;
    public final int aliveAnimals;
    public final int plants;
    public final double averageEnergy;
    public final double lifeExpectancy;

    public DailyStats(int day, int aliveAnimals, int plants, double averageEnergy, double lifeExpectancy) {
        this.day = day;
        this.aliveAnimals = aliveAnimals;
        this.plants = plants;
        this.averageEnergy = averageEnergy;
        this.lifeExpectancy = lifeExpectancy;
    }
}
