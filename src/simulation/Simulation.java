package simulation;

import java.util.ArrayList;
import java.util.List;

public class Simulation {

    private List<Day> day = new ArrayList<Day>();
    private final BoardParams params;
    private int currentDay = 0;
    private final String name;


    public Simulation(String name, BoardParams params) {
        this.name = name;
        this.params = params;
    }



    public int getCurrentDay() {
        return currentDay;
    }

    public String getName() {
        return name;
    }

    public BoardParams getParams() {
        return this.params;
    }
}
