package simulation.stats;

import simulation.Animal;
import simulation.Genome;
import simulation.Simulation;

import java.util.*;
import java.util.stream.Collectors;

public class StatsWatcher {
    private final Simulation simulation;
    public final Summarizer summarizer;

    public StatsWatcher(Simulation s){
        this.simulation = s;
        this.summarizer = new Summarizer(s);
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

    public Optional<Genome> getDominatingGenome(){
        Map<Genome,Integer> occurrences = new TreeMap<>();
        for (Animal animal : simulation.animalBoard.getAllAlive()){
            Genome genome = animal.getGenome();
            if (!occurrences.containsKey(genome)){
                occurrences.put(genome,1);
            }
            else occurrences.put(genome,occurrences.get(genome)+1);
        }
        return occurrences.keySet().stream().max(Comparator.comparingInt(occurrences::get));
    }

    public double getAverageChildrenNumber(){
        return simulation.animalBoard.getAllAlive().stream()
                .map(a -> a.getChildren().size())
                .reduce(0,Integer::sum)
                .doubleValue()
                /simulation.animalBoard.getAllAlive().size();
    }

    public List<Animal> getAnimalsWithDominatingGenome(){
        if(getDominatingGenome().isPresent())
            return simulation.animalBoard.getAllAlive().stream()
                .filter(a -> a.getGenome().equals(getDominatingGenome().get()))
                .collect(Collectors.toList());
        else return new ArrayList<>();
    }

    public double getAverageLifeExpectancy(){
        if (simulation.animalBoard.getAllDead().isEmpty())
            return 0;
        else return ((Integer)simulation.animalBoard.getAllDead()
                .stream().mapToInt(a -> a.getDeathDay() - a.getBirthDay())
                .sum()).doubleValue() / simulation.animalBoard.getAllDead().size();
    }

    public double toTwoDecimalPlaces(double v){
        return OutputUtils.toTwoDecimalPlaces(v);
    }

    public String getDailyStatsAndUpdateSummarizer(){
        summarizer.add(new DailyStats(
                simulation.getCurrentDay(),
                getAliveAnimalsNum(),
                getPlantsNum(),
                getAverageEnergy(),
                getAverageLifeExpectancy()
        ));
        return  "Current day: " + simulation.getCurrentDay() +
                "\nAlive animals: " + getAliveAnimalsNum() +
                "\nPlants: " + getPlantsNum() +
                "\nAverage energy: " + OutputUtils.toTwoDecimalPlaces(getAverageEnergy()) +
                "\nAverage children number: " + OutputUtils.toTwoDecimalPlaces(getAverageChildrenNumber()) +
                "\nAverage life expectancy: " + OutputUtils.toTwoDecimalPlaces(getAverageLifeExpectancy()) +
                "\nDominating genome: " + (getDominatingGenome().isPresent() ? getDominatingGenome().get(): "none")+
                "\nAnimals with dominating genome: " + getAnimalsWithDominatingGenome().size();
    }
}
