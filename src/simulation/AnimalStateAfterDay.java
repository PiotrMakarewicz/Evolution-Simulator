package simulation;

import java.util.ArrayList;

public class AnimalStateAfterDay {
    public final Location location;
    public final Direction direction;
    public final int energy;
    public final ArrayList<Animal> childrenBornOnThisDay;

    public AnimalStateAfterDay(Location location, Direction direction, int energy, ArrayList<Animal> childrenBornOnThisDay) {
        this.location = location;
        this.direction = direction;
        this.energy = energy;
        this.childrenBornOnThisDay = childrenBornOnThisDay;
    }
}
