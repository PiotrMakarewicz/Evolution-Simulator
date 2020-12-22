package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AnimalBoard {
    private AbstractMap<Location, List<Animal>> locationAnimalMap = new TreeMap<>();
    private List<Animal> deadAnimals = new ArrayList<>();

    public List<Animal> getAllAlive(){
        List<Animal> allAnimals = new ArrayList<Animal>();
        for (List<Animal> locationAnimals : locationAnimalMap.values()){
            allAnimals = Stream.concat(allAnimals.stream(),locationAnimals.stream()).collect(Collectors.toList());
        }
        return allAnimals;
    }
    public void insert(Animal animal){
        List<Animal> locationAnimals = locationAnimalMap.get(animal.getLocation());
        if(locationAnimals != null){
            locationAnimals.add(animal);
        }
        else {
            locationAnimalMap.put(animal.getLocation(), new ArrayList<Animal>());
            locationAnimalMap.get(animal.getLocation()).add(animal);
        }
    }

    public List<Location> locations(){
        return locationAnimalMap.keySet().stream().collect(Collectors.toList());
    }



    public void remove(Animal animal) {
        Location locationToRemove = null;
        for (Map.Entry<Location, List<Animal>> entry : locationAnimalMap.entrySet()) {
            Location location = entry.getKey();
            List<Animal> animals = entry.getValue();
            if (animals.contains(animal)) {
                animals.remove(animal);
                if (animals.isEmpty())
                    locationToRemove = location;
            }
        }
        if (locationToRemove != null) {
            locationAnimalMap.remove(locationToRemove);
        }
    }

    public void markAsDead(Animal animal){
        remove(animal);
        deadAnimals.add(animal);
    }

    public boolean noAnimalsAt(int x, int y){
        return noAnimalsAt(new Location(x,y));
    }

    public List<Location> getAnimalLocations(){
        return new ArrayList<>(this.locationAnimalMap.keySet());
    }

    public boolean noAnimalsAt(Location location){
        return locationAnimalMap.get(location) == null;
    }

    public List<Animal> get(Location location){
        List<Animal> animals = locationAnimalMap.get(location);
        if (animals == null) return new ArrayList<Animal>();
        else return animals;
    }

    public List<Animal> get(int x, int y){
        return get(new Location(x,y));

    }
    public void update(){
        List<Animal> animals = this.getAllAlive();
        locationAnimalMap = new TreeMap<>();
        for (Animal animal : animals){
            if (animal.isAlive()){
                insert(animal);
            }
        }
    }
}
