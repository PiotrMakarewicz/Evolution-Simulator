package simulation;

import java.util.AbstractMap;
import java.util.List;
import java.util.TreeMap;
import java.util.stream.Collectors;

public class PlantBoard {
    private AbstractMap<Location,Integer> plantedSpots = new TreeMap<Location,Integer>();
    public void plant(int x, int y){
        plant(new Location(x,y));
    }
    public void plant(Location location){
        if (isPlanted(location)){
            int plantsNum = getPlantedSpots().get(location);
            getPlantedSpots().remove(location);
            getPlantedSpots().put(location,plantsNum+1);
        }
        else{
            getPlantedSpots().put(location,1);
        }
    }
    public void unplant(Location location) throws UnplantingUnplantedLocationException{
        if (! isPlanted(location)){
            throw new UnplantingUnplantedLocationException("Trying to unplant an unplanted location"
                    + "x: " + location.getX() + "y: "+ location.getY()  + "\n");
        }
        else {
            int plantsNum = getPlantedSpots().get(location);
            getPlantedSpots().remove(location);
            if (plantsNum != 1){
                getPlantedSpots().put(location,plantsNum+-1);
            }
        }
    }

    public void unplant(int x, int y) throws UnplantingUnplantedLocationException{
        unplant(new Location(x,y));
    }
    public boolean isPlanted(int x, int y){
        return isPlanted(new Location(x,y));
    }
    public boolean isPlanted(Location location){
        return getPlantedSpots().containsKey(location);
    }

    public List<Location> getPlantedLocations() {
        return plantedSpots.keySet().stream().collect(Collectors.toList());
    }

    public AbstractMap<Location, Integer> getPlantedSpots() {
        return plantedSpots;
    }
}
