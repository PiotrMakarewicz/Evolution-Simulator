package sample;

import simulation.Simulation;

public class TerrainTileFactory {
    private Simulation simulation;
    TerrainTileFactory(Simulation simulation){
        this.simulation = simulation;
    }
    public TerrainTile getTile(int x, int y){
        if (simulation.plantBoard.isPlanted(x,y)){
            if (simulation.jungle.contains(x,y))
                return TerrainTile.JUNGLEPLANTED;
            else return TerrainTile.SAVANNAHPLANTED;
        }
        else{
            if(simulation.jungle.contains(x,y))
                return TerrainTile.JUNGLE;
            else return TerrainTile.SAVANNAH;
        }
    }
}
