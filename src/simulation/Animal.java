package simulation;

import java.util.ArrayList;
import java.util.List;

public class Animal{
    private final int birthDay;
    private int deathDay;
    private final Genome genome;
    private Direction direction;
    private int energy;

    Animal(int energy, int birthDay){
        this.birthDay = birthDay;
        this.energy = energy;
        this.genome = new Genome();
    };

    Animal(Animal parent1, Animal parent2, int birthDay){
        this.genome = new Genome(parent1.getGenome(), parent2.getGenome());
        this.energy = parent1.energy / 4 + parent2.energy / 4;
        this.birthDay = birthDay;
    }

    public void die(int deathDay) throws AnimalStateException {
        if (!this.isAlive())
            throw new AnimalStateException("Trying to kill a dead animal: " + this.toString());
        this.energy = 0;
        this.deathDay = deathDay;
    }
    public void shift() {
        direction = direction.shiftedBy(genome.pickRandomGene());
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

    public Direction getDirection() {
        return direction;
    }

    public boolean isAlive() {
        return energy > 0;
    }

    public int getEnergy() {
        return energy;
    }
}
