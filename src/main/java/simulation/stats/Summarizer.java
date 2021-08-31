package simulation.stats;

import simulation.Animal;
import simulation.Genome;
import simulation.Simulation;

import java.util.*;

public class Summarizer {
    private final Simulation simulation;
    private final List<DailyStats> dailyStatsList = new ArrayList<>();
    public Summarizer(Simulation s){
        this.simulation = s;
    }
    public void add(DailyStats s){
        if(dailyStatsList.isEmpty() || getLastDay() != s.day){
            dailyStatsList.add(s);
        }
    }
    public int getLastDay(){
        return dailyStatsList.isEmpty() ? 0 : getLastDayStats().day;
    }

    public String getSummary(){
        return  "Days: " + getLastDay() +
                "\nTotal animals: " + getTotalAnimals() +
                "\nAverage alive animals each day: " + OutputUtils.toTwoDecimalPlaces(getAverageAliveAnimals()) +
                "\nAverage plants each day: " + OutputUtils.toTwoDecimalPlaces(getAveragePlants()) +
                "\nAverage energy each day: " + OutputUtils.toTwoDecimalPlaces(getAverageEnergy()) +
                "\nAverage children number: " + OutputUtils.toTwoDecimalPlaces(getAverageChildrenNumber()) +
                "\nAverage life expectancy: " + OutputUtils.toTwoDecimalPlaces(getAverageLifeExpectancy()) +
                "\nDominating genome: " + (getDominatingGenome().isPresent() ? getDominatingGenome().get(): "none")+
                "\nAnimals with dominating genome: " + getAnimalsWithDominatingGenome();
    }

    private int getAnimalsWithDominatingGenome() {
        return ((Long) simulation.animalBoard.getAllAnimals().stream()
                .filter(a -> a.getGenome().equals(getDominatingGenome().get())).count())
                .intValue();
    }

    private Optional<Genome> getDominatingGenome() {
        Map<Genome,Integer> occurrences = new TreeMap<>();
        for (Animal animal : simulation.animalBoard.getAllAnimals()){
            Genome genome = animal.getGenome();
            if (!occurrences.containsKey(genome)){
                occurrences.put(genome,1);
            }
            else occurrences.put(genome,occurrences.get(genome)+1);
        }
        return occurrences.keySet().stream().max(new Comparator<Genome>() {
            @Override
            public int compare(Genome o1, Genome o2) {
                return occurrences.get(o1)-occurrences.get(o2);
            }
        });
    }

    private int getTotalAnimals(){
        return simulation.animalBoard.getAllAlive().size() + simulation.animalBoard.getAllDead().size();
    }

    private double getAverageAliveAnimals() {
        OptionalDouble od = dailyStatsList.stream().mapToDouble(ds -> ds.aliveAnimals).average();
        if(od.isPresent()) return od.getAsDouble();
        else return 0;
    }

    private double getAveragePlants(){
        OptionalDouble od = dailyStatsList.stream().mapToDouble(ds -> ds.plants).average();
        if(od.isPresent()) return od.getAsDouble();
        else return 0;
    }

    private double getAverageEnergy(){
        OptionalDouble od = dailyStatsList.stream().mapToDouble(ds -> ds.averageEnergy).average();
        if(od.isPresent()) return od.getAsDouble();
        else return 0;
    }

    private double getAverageChildrenNumber(){
        int total = getTotalAnimals();
        int initial = simulation.getInitialAnimalsNum();
        return ((Integer)(total - initial)).doubleValue()/ total;
    }

    private double getAverageLifeExpectancy(){
        if (dailyStatsList.isEmpty()) return 0;
        else return getLastDayStats().lifeExpectancy;
    }

    private DailyStats getLastDayStats() {
        return dailyStatsList.get(dailyStatsList.size() - 1);
    }
}
