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
    public final Jungle jungle;
    private final double moveEnergy;
    private final double plantEnergy;
    private final int initialAnimalsNum;
    private final double initialEnergy;
    private List<Location> eatenPlants = new ArrayList<Location>();
    private List<Location> previousAnimalLocations = new ArrayList<Location>();

    public Simulation(String name, int width, int height, double jungleRatio, double moveEnergy, double plantEnergy, int initialAnimalsNum, double initialEnergy) throws InvalidStartingParametersException{
        this.name = name;
        this.width = width;
        this.height = height;
        this.initialEnergy = initialEnergy;
        try {
            this.jungle = new Jungle(width, height, jungleRatio);
        } catch(InvalidRectangleException e){
            throw new InvalidStartingParametersException(e.toString() + " This may have occurred due to jungleRatio or board dimensions being too small.");
        }
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.minimalReproduceEnergy = initialEnergy/2;
        this.initialAnimalsNum = initialAnimalsNum;
    }

    private void addPlants(){
        boolean plantedInJungle = false;
        boolean plantedOutsideJungle = false;
        while (!plantedInJungle || !plantedOutsideJungle){
            Location location = Location.getRandom(width,height);
            if(jungle.contains(location) && !plantedInJungle && animalBoard.noAnimalsAt(location)){
                plantBoard.plant(location);
                System.out.println("Added in-jungle plant at "+location.toString());
                plantedInJungle = true;
            }
            else if(!jungle.contains(location) && !plantedOutsideJungle && animalBoard.noAnimalsAt(location)){
                plantBoard.plant(location);
                System.out.println("Added outside-jungle plant at "+location.toString());
                plantedOutsideJungle = true;
            }
        }
    }

    private void moveAnimals() throws AnimalStateException {
        previousAnimalLocations = animalBoard.getAnimalLocations();
        for (Animal animal : animalBoard.getAll()) {
            animal.setEnergy(animal.getEnergy()-moveEnergy);
            if (animal.getEnergy() < 0) {
                animal.die(currentDay);
            } else {
                System.out.println("Moving "+animal.toString()+" from " + animal.getLocation() +" to "+toBoardLimits(animal.getLocation().stepTo(animal.getDirection())));
                animal.shift();
                animal.setLocation(toBoardLimits(animal.getLocation().stepTo(animal.getDirection())));
            }
            animalBoard.update();
        }
    }

    public Location toBoardLimits(Location location){
        return new Location((location.getX()+width)%width, (location.getY()+height)%height);
    }
    public void eatPlants() throws UnplantingUnplantedLocationException{
        eatenPlants = new ArrayList<>();
        for (Location location : plantBoard.getPlantedLocations()) {
            List<Animal> animals = animalBoard.get(location);
            if (!animals.isEmpty()) {
                double highestEnergy = animals.stream()
                        .max(new AnimalEnergyComparator())
                        .get()
                        .getEnergy();
                List<Animal> highestEnergyAnimals = animals.stream()
                        .filter((animal -> Math.abs(animal.getEnergy() - highestEnergy) < 1e-4))
                        .collect(Collectors.toList());
                int n = highestEnergyAnimals.size();
                for (Animal animal : highestEnergyAnimals) {
                    animal.addEnergy(plantEnergy / n);
                }
                plantBoard.unplant(location);
                eatenPlants.add(location);
                System.out.println("Plant eaten at " + location.toString() + " by " + animals.get(0).toString());
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
    }

    public void simulateOneDay() throws SimulationErrorException{
        currentDay++;
        System.out.println("\nSTARTING DAY "+ currentDay + "\n=======================================");
        try {
            System.out.println("\nMoving animals\n=======================================");
            moveAnimals();
            System.out.println("\nEating plants\n=======================================");
            eatPlants();
            System.out.println("\nReproducing animals\n=======================================");
            reproduceAnimals();
            System.out.println("\nAdding plants\n=======================================");
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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public List<Location> getEatenPlants() {
        return eatenPlants;
    }

    public List<Location> getPreviousAnimalLocations() {
        return previousAnimalLocations;
    }
}
