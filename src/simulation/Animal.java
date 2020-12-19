package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Animal{
    private final int birthDay;
    private int deathDay = -1;
    private final Genome genome;
    private Direction direction;
    private double energy;
    private Location location;

    Animal(double energy, int birthDay, Location location){

        this.birthDay = birthDay;
        this.energy = energy;
        this.genome = new Genome();
        this.location = location;
        this.direction = Direction.values()[(new Random(System.nanoTime()).nextInt(8))];
        System.out.println("Spawning "+this.toString()+" at "+location.toString());
    };

    Animal(Animal parent1, Animal parent2, int birthDay, Location location){

        this.genome = new Genome(parent1.getGenome(), parent2.getGenome());
        this.energy = parent1.energy / 4 + parent2.energy / 4;
        this.birthDay = birthDay;
        this.location = location;
        this.direction = Direction.values()[(new Random(System.nanoTime()).nextInt(8))];
        System.out.println("Born "+this.toString()+", child of "+parent1.toString()+" and "+parent2.toString()+", at "+location);
    }

    public void die(int deathDay) throws AnimalStateException {
        System.out.println(this.toString()+" dies at "+this.location);
        if (!this.isAlive())
            throw new AnimalStateException("Trying to kill a dead animal: " + this.toString());
        this.energy = 0;
        this.deathDay = deathDay;
    }
    public void shift() {
        direction = direction.shiftedBy(genome.pickRandomGene());
        System.out.println(this.toString() + " shifts to " + this.direction);
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
        return this.deathDay == -1;
    }

    public double getEnergy() {
        return energy;
    }

    public void addEnergy(double energy){
        this.energy += energy;
    }

    public Location getLocation() { return location; }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setEnergy(double energy) {
        this.energy = energy;
    }

    @Override
    public String toString() {
        return genome.toString() +
                "-" + birthDay;
    }
}
