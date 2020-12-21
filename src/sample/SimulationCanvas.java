package sample;

import javafx.scene.canvas.Canvas;
import simulation.Simulation;

public class SimulationCanvas extends Canvas {
    private final Simulation simulation;
    private final TerrainTileFactory terrainTileFactory;
    private final AnimalTileFactory animalTileFactory;
    private final int tileSize = 5;
    SimulationCanvas(Simulation simulation){
        super(simulation.getWidth()*10,simulation.getHeight()*10);
        this.simulation = simulation;
        this.terrainTileFactory = new TerrainTileFactory(simulation);
        this.animalTileFactory = new AnimalTileFactory(simulation);
    }
    public void update(){
        for (int i = 0; i<simulation.getWidth(); i++){
            for (int j = 0; j<simulation.getHeight(); j++){
                drawTile(i,j, terrainTileFactory.getTile(i,j));
                drawTile(i,j,animalTileFactory.getTile(i,j));
            }
        }
    }
    private void drawTile(int x, int y, Tile tile){
        int drawX = x*tileSize;
        int drawY = y*tileSize;
        this.getGraphicsContext2D().drawImage(tile.getImage(),drawX,drawY);
        System.out.println("Drawing "+tile.toString()+" at "+drawX+" "+drawY);
    }

}
