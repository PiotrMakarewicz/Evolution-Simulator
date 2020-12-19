package simulation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

public class Simulation {

    private final double minimalReproduceEnergy;
    private int currentDay = 0;
    public final String name;
    public final AnimalBoard animalBoard = new AnimalBoard();
    public final PlantBoard plantBoard = new PlantBoard();
    private final int width;
    private final int height;
    private final Jungle jungle;
    private final double moveEnergy;
    private final double plantEnergy;
    private final int initialAnimalsNum;
    private final double initialEnergy;

    public Simulation(String name, int width, int height, double jungleRatio, double moveEnergy, double plantEnergy, int initialAnimalsNum, double initialEnergy) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.initialEnergy = initialEnergy;
        this.jungle = new Jungle(width,height,jungleRatio);
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.minimalReproduceEnergy = 2*moveEnergy;
        this.initialAnimalsNum = initialAnimalsNum;
    }

    private void addPlants(){
        boolean plantedInJungle = false;
        boolean plantedOutsideJungle = false;
        while (!plantedInJungle || !plantedOutsideJungle){
            Location location = Location.getRandom(width,height);
            if(jungle.contains(location) && !plantedInJungle){
                plantBoard.plant(location);
                plantedInJungle = true;
            }
            else if(!jungle.contains(location) && !plantedOutsideJungle){
                plantBoard.plant(location);
                plantedOutsideJungle = true;
            }
        }
    }

    private void moveAnimals() throws AnimalStateException {
        for (Animal animal : animalBoard.getAll()) {
            animal.setEnergy(animal.getEnergy()-moveEnergy);
            if (animal.getEnergy() < 0) {
                animal.die(currentDay);
            } else {
                System.out.println("Moving animal from " + animal.getLocation() +" to "+toBoardLimits(animal.getLocation().stepTo(animal.getDirection())));
                animal.setLocation(toBoardLimits(animal.getLocation().stepTo(animal.getDirection())));
            }
            animalBoard.update();
        }
    }

    public Location toBoardLimits(Location location){
        return new Location(location.getX()%width, location.getY()%height);
    }
    public void eatPlants() throws UnplantingUnplantedLocationException{
        for (Location location : plantBoard.getPlantedLocations()) {
            plantBoard.unplant(location);
            List<Animal> animals = animalBoard.get(location);
            if (animals != null) {
                double highestEnergy = animals.stream()
                        .max(new AnimalEnergyComparator())
                        .get()
                        .getEnergy();
                List<Animal> highestEnergyAnimals = animals.stream()
                        .filter((animal -> animal.getEnergy() == highestEnergy))
                        .collect(Collectors.toList());
                int n = highestEnergyAnimals.size();
                for (Animal animal : highestEnergyAnimals) {
                    animal.addEnergy(plantEnergy / n);
                }
            }
        }
    }

    public void reproduceAnimals(){
        for (Location location : animalBoard.locations()){
            List<Animal> possibleParents = new ArrayList<>();
            for (Animal animal : animalBoard.get(location)){
                if (animal.getEnergy() > minimalReproduceEnergy && animal.getBirthDay() < currentDay){
                    possibleParents.add(animal);
                }
            }
            if (possibleParents.size() >= 2) {
                possibleParents.sort(new AnimalEnergyComparator());
                List<Animal> parents = possibleParents
                        .stream().limit(2).collect(Collectors.toList());
                animalBoard.insert(
                        new Animal(
                                parents.get(0),
                                parents.get(1),
                                currentDay,
                                getChildLocation(location)));
                for (Animal parent : parents) {
                    parent.setEnergy(parent.getEnergy() * 0.75);
                }
            }
        }
    }

    Location getChildLocation(Location location){
        List<Direction> candidateDirections = new ArrayList<>();
        Random rng = new Random(System.nanoTime());
        for (Direction direction : Direction.values()){
            Location target = toBoardLimits(location.stepTo(direction));
            if (!plantBoard.isPlanted(target) && animalBoard.noAnimalsAt(target)){
                candidateDirections.add(direction);
            }
        }
        if (candidateDirections.isEmpty()) {
           candidateDirections = Arrays.stream(Direction.values()).collect(Collectors.toList());;
        }
        return toBoardLimits(location.stepTo(candidateDirections.get(rng.nextInt(candidateDirections.size()))));
    }
    public void addInitialAnimals(){
        for (int i = 0; i<initialAnimalsNum;i++){
            animalBoard.insert(new Animal(initialEnergy,0,Location.getRandom(width,height)));
        }
    }

    public void start(){
        addInitialAnimals();
        try {
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
            simulateOneDay();
        } catch (SimulationErrorException e){
            System.out.println(e.toString());
        }
    }

    public void simulateOneDay() throws SimulationErrorException{
        currentDay++;
        System.out.println("Starting day "+ currentDay);
        try {
            System.out.println("Moving animals");
            moveAnimals();
            System.out.println("Eating plants");
            eatPlants();
            System.out.println("Reproducing animals");
            reproduceAnimals();
            System.out.println("Adding plants");
            addPlants();
        } catch(AnimalStateException e){
            throw new SimulationErrorException("AnimalStateException: "+ e.toString(),this);
        } catch (UnplantingUnplantedLocationException e){
            throw new SimulationErrorException("UnplantingUnplantedLocationException: " + e.toString(),this);
        }
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public String getName() {
        return name;
    }
}
