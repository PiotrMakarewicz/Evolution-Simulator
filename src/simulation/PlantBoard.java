package simulation;

import java.util.AbstractMap;
import java.util.TreeMap;

public class PlantBoard {
    AbstractMap<Location,Integer> plantedSpots = new TreeMap<Location,Integer>();
    public void plant(int x, int y){
        plant(new Location(x,y));
    }
    public void plant(Location location){
        if (isPlanted(location)){
            int plantsNum = plantedSpots.get(location);
            plantedSpots.remove(location);
            plantedSpots.put(location,plantsNum+1);
        }
        else{
            plantedSpots.put(location,1);
        }
    }
    public void unplant(int x, int y) throws UnplantingUnplantedLocationException{
        if (! isPlanted(x,y)){
            throw new UnplantingUnplantedLocationException("Trying to unplant an unplanted location"
            + "x: " + x + "y: "+ y + "\n");
        }
        else {
            Location location = new Location(x,y);
            int plantsNum = plantedSpots.get(location);
            plantedSpots.remove(location);
            if (plantsNum != 1){
                plantedSpots.put(location,plantsNum+-1);
            }
        }
    }
    public boolean isPlanted(int x, int y){
        return isPlanted(new Location(x,y));
    }
    public boolean isPlanted(Location location){
        return plantedSpots.containsKey(location);
    }
}
