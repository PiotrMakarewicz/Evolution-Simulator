package sample;

import javafx.scene.image.Image;
import javafx.scene.canvas.Canvas;
import simulation.Location;
import simulation.Simulation;


import java.util.List;

public class SimulationCanvas extends Canvas {
    private final Simulation simulation;
    private final TerrainTileFactory terrainTileFactory;
    private final AnimalTileFactory animalTileFactory;
    private final int tileSize = 10;
    private final Image highlightAnimalImage = new Image("images/highlight-animal.png");

    SimulationCanvas(Simulation simulation){
        super(simulation.getWidth()*10,simulation.getHeight()*10);
        this.simulation = simulation;
        this.terrainTileFactory = new TerrainTileFactory(simulation);
        this.animalTileFactory = new AnimalTileFactory(simulation);
    }
    public void drawBackground(){
        for (int i = 0; i<simulation.getWidth(); i++){
            for (int j = 0; j<simulation.getHeight(); j++){
                drawTile(i,j, terrainTileFactory.getTile(i,j));
                if (!simulation.animalBoard.noAnimalsAt(i,j))
                    drawTile(i,j, animalTileFactory.getTile(i,j));
            }
        }
    }
    public void update(){
        for (Location location : simulation.getEatenPlants()){
            int x = location.getX();
            int y = location.getY();
            drawTile(x,y,terrainTileFactory.getTile(x,y));
        }

        for (Location location : simulation.getPreviousAnimalLocations()) {
            int x = location.getX();
            int y = location.getY();
            if(simulation.animalBoard.noAnimalsAt(x,y))
                drawTile(x,y,terrainTileFactory.getTile(x,y));
            else drawTile(x,y,animalTileFactory.getTile(x,y));
        }
        for (Location location : simulation.plantBoard.getPlantedLocations()){
            int x = location.getX();
            int y = location.getY();
            drawTile(x,y,terrainTileFactory.getTile(x,y));
        }
        for (Location location : simulation.animalBoard.getAnimalLocations()){
            int x = location.getX();
            int y = location.getY();
            drawTile(x,y,animalTileFactory.getTile(x,y));
        }

    }
    private void drawTile(int x, int y, Tile tile){
        int drawX = x* tileSize;
        int drawY = y* tileSize;
        this.getGraphicsContext2D().drawImage(tile.getImage(),drawX,drawY);
    }

    public void highlightLocations(List<Location> locations){
        for (Location location :locations){
            int x = location.getX()*tileSize;
            int y = location.getY()*tileSize;
            this.getGraphicsContext2D().drawImage(highlightAnimalImage,x,y);
        }
    }

}
