package simulation;

import java.util.*;

public class Board {
    private AbstractSet<Location> plantedSpots = new TreeSet<>();
    private AbstractMap<Location, List<Animal>> animalLocations = new TreeMap<>();
    private AbstractMap<Genome,Integer> dominantGenomes = new TreeMap<>();
}
