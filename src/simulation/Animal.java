package simulation;

import java.util.ArrayList;
import java.util.List;

public class Animal{
    private final int birthDay;
    private int deathDay;
    private final Genome genome;
    private Simulation simulation;

    private Location location;
    private Direction direction;
    private int energy;


    private List<AnimalStateAfterDay> stateAfterEachDay = new ArrayList<>();

    Animal(Animal parent1, Animal parent2, int energy){
        this.simulation = parent1.simulation;
        this.genome = new Genome(parent1.getGenome(), parent2.getGenome());
        this.energy = energy;
        this.birthDay = simulation.getCurrentDay();
    }

    public void kill() throws AnimalStateException {
        if (!this.isAlive())
            throw new AnimalStateException("Trying to kill a dead animal: " + this.toString());
        this.energy = 0;
        this.deathDay = simulation.getCurrentDay();
    }
    public Direction shift() {
        return direction = direction.shiftedBy(genome.pickRandomGene());
    }

    public Location move() throws AnimalStateException {
        if (!this.isAlive())
            throw new AnimalStateException("Trying to move a dead animal: " + this.toString());
        if (this.energy < simulation.getParams().getMoveEnergy())
            this.kill();
        this.energy -= simulation.getParams().getMoveEnergy();
        int boardWidth = simulation.getParams().getWidth();
        int boardHeight = simulation.getParams().getHeight();
        int newX = (location.getX() + direction.getX() + boardWidth) % boardWidth;
        int newY = (location.getY() + direction.getY() + boardHeight) % boardHeight;
        return location = new Location(newX,newY);
    }

    private void saveStateAfterDay(){

    }


    public int getBirthDay() {
        return birthDay;
    }

    public int getDeathDay() {
        return deathDay;
    }

    public Genome getGenome() {
        return genome;
    }

    public Location getLocation() {
        return location;
    }

    public Direction getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public int getEnergy() {
        return energy;
    }

    public List<AnimalStateAfterDay> getStateAfterEachDay() {
        return stateAfterEachDay;
    }
}
