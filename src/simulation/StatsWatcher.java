package simulation;

import java.sql.SQLOutput;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class StatsWatcher {
    private final Simulation simulation;
    public StatsWatcher(Simulation s){
        this.simulation = s;
    }
    public int getAliveAnimalsNum(){
        return simulation.animalBoard.getAllAlive().size();
    }
    public int getPlantsNum(){
        return simulation.plantBoard.getPlantedLocations().size();
    }
    public double getAverageEnergy(){
        List<Animal> animals = simulation.animalBoard.getAllAlive();
        double totalEnergy = animals.stream().map(Animal::getEnergy).reduce(0.0, Double::sum);
        return totalEnergy/animals.size();
    }
    public Genome getDominatingGenome(){
        Map<Genome,Integer> occurrences = new TreeMap<>();
        for (Animal animal : simulation.animalBoard.getAllAlive()){
            Genome genome = animal.getGenome();
            if (!occurrences.containsKey(genome)){
                occurrences.put(genome,1);
            }
            else occurrences.put(genome,occurrences.get(genome)+1);
        }
        return occurrences.keySet().stream().max(Comparator.comparingInt(occurrences::get)).get();
    }
    public double getAverageChildrenNumber(){
        return simulation.animalBoard.getAllAlive().stream()
                .map(a -> a.getChildren().size())
                .reduce(0,Integer::sum)
                .doubleValue()
                /simulation.animalBoard.getAllAlive().size();
    }
    public List<Animal> getAnimalsWithDominatingGenome(){
        return simulation.animalBoard.getAllAlive().stream()
                .filter(a -> a.getGenome().equals(getDominatingGenome()))
                .collect(Collectors.toList());
    }

    public void printStats(){
        System.out.println("\nSimulation "+ simulation.name+" statistics:");
        System.out.println("=======================================");
        System.out.println("Alive animals: " +getAliveAnimalsNum());
        System.out.println("Plants: "+getPlantsNum());
        System.out.println("Average energy: "+getAverageEnergy());
        System.out.println("Dominating genome: "+getDominatingGenome());
        System.out.println("Average children number:"+getAverageChildrenNumber());
        System.out.println("Animals with dominating genome: "+getAnimalsWithDominatingGenome());
    }
}
