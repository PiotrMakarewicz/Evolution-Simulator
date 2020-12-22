package sample;

import javafx.scene.image.Image;

public enum TerrainTile implements Tile{
    JUNGLE("images/jungle-tile.png"),
    JUNGLEPLANTED("images/jungle-tile-planted.png"),
    SAVANNAH("images/savannah-tile.png"),
    SAVANNAHPLANTED("images/savannah-tile-planted.png");
    private final Image image;

    TerrainTile(String imagePath){
        this.image = new Image(imagePath);
    }

    public Image getImage(){
        return this.image;
    }
}
