package simulation;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Board {
    private AbstractSet<Location> plantedSpots = new TreeSet<>();
    private AbstractMap<Location, List<Animal>> locationAnimalMap = new TreeMap<>();
    public List<Animal> getAnimalList(){
        List<Animal> allAnimals = new ArrayList<Animal>();
        for (List<Animal> locationAnimals : locationAnimalMap.values()){
            allAnimals = Stream.concat(allAnimals.stream(),locationAnimals.stream()).collect(Collectors.toList());
        }
        return allAnimals;
    }
    public void insert(Animal animal, int x, int y){
        locationAnimalMap.get(new Location(x,y)).add(animal);
    }
    public List<Animal> getAnimalsAt(int x, int y){
        return locationAnimalMap.get(new Location(x,y));
    }
}
