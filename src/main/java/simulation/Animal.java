package simulation;

import simulation.exceptions.AnimalStateException;

import java.util.*;

public class Animal implements Comparable<Animal>{
    private final int birthDay;
    private int deathDay = -1;
    private final Genome genome;
    private Direction direction;
    private double energy;
    private Location location;
    private final List<Animal> children = new ArrayList<>();

    Animal(double energy, int birthDay, Location location){
        this.birthDay = birthDay;
        this.energy = energy;
        this.genome = new Genome();
        this.location = location;
        this.direction = Direction.values()[(new Random(System.nanoTime()).nextInt(8))];
    }

    Animal(Animal parent1, Animal parent2, int birthDay, Location location){
        this.genome = new Genome(parent1.getGenome(), parent2.getGenome());
        this.energy = parent1.energy / 4 + parent2.energy / 4;
        this.birthDay = birthDay;
        this.location = location;
        this.direction = Direction.values()[(new Random(System.nanoTime()).nextInt(8))];
        parent1.children.add(this);
        parent2.children.add(this);
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
        return "Genome: " + genome + "\n"
                + "Location: " + location + "\n"
                + "Born on day: " + birthDay + "\n"
                + (this.isAlive() ? "Energy: " + energy : "Died on day: " + deathDay) + "\n"
                + "Children: " + getChildrenNumber() +"\n"
                + "Ancestors: " + getAncestorsNumber();
    }

    public List<Animal> getChildren() {
        return children;
    }

    public int compareTo(Animal a){
        return this.hashCode() - a.hashCode();
    }

    public int getChildrenNumber(){
        return this.children.size();
    }

    public int getAncestorsNumber(){
        Set<Animal> visited = new TreeSet<>();
        this.traverse(visited);
        return visited.size() - 1;
    }

    public void traverse(Set<Animal> visited){
        if(!visited.contains(this)){
            visited.add(this);
            for (Animal child : this.children){
                child.traverse(visited);
            }
        }
    }

}
