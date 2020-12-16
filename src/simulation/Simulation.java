package simulation;

import java.util.AbstractMap;
import java.util.List;
import java.util.TreeMap;

public class Simulation {

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

    public Simulation(String name, int width, int height, double jungleRatio, double moveEnergy, double plantEnergy, int initialAnimalsNum) {
        this.name = name;
        this.width = width;
        this.height = height;
        this.jungle = new Jungle(width,height,jungleRatio);
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
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
                animal.setLocation(correctOverlap(animal.getLocation().stepTo(animal.getDirection())));
            }
            animalBoard.update();
        }
    }

    public Location correctOverlap(Location location){
        return new Location(location.getX()%width, location.getY()%height);
    }

    public void start(){
    }



    public void simulateOneDay() throws SimulationErrorException{
        try {
            moveAnimals();
            addPlants();
        }catch(AnimalStateException e){
            throw new SimulationErrorException(e.toString());
        }
    }

    public int getCurrentDay() {
        return currentDay;
    }

    public String getName() {
        return name;
    }
}
