package simulation;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class Simulation {

    private int currentDay = 0;
    public final String name;
    public final Board board = new Board();
    private final int width;
    private final int height;
    private final double jungleRatio;
    private final double moveEnergy;
    private final double plantEnergy;
    private final int initialAnimalsNum;
    private AbstractMap<Genome,Integer> dominantGenomes = new TreeMap<>();

    public Simulation(String name, int width, int height, double jungleRatio, double moveEnergy, double plantEnergy, int initialAnimalsNum) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.initialAnimalsNum = initialAnimalsNum;
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public String getName() {
        return name;
    }
}
