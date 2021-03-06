package simulation;

import simulation.exceptions.UnplantingUnplantedLocationException;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;

public class PlantBoard {
    private AbstractMap<Location,Integer> plantedSpots = new TreeMap<Location,Integer>();

    public void plant(Location location){
        if (!isPlanted(location))
            getPlantedSpots().put(location,1);
    }
    public void unplant(Location location) throws UnplantingUnplantedLocationException {
        if (! isPlanted(location)){
            throw new UnplantingUnplantedLocationException("Trying to unplant an unplanted location"
                    + "x: " + location.getX() + "y: "+ location.getY()  + "\n");
        }
        else {
            getPlantedSpots().remove(location);
        }
    }

    public boolean isPlanted(int x, int y){
        return isPlanted(new Location(x,y));
    }
    public boolean isPlanted(Location location){
        return getPlantedSpots().containsKey(location);
    }

    public List<Location> getPlantedLocations() {
        return new ArrayList<>(plantedSpots.keySet());
    }

    public AbstractMap<Location, Integer> getPlantedSpots() {
        return plantedSpots;
    }
}
