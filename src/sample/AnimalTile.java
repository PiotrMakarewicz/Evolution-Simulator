package sample;

import javafx.scene.image.Image;

public enum AnimalTile implements Tile{
    ANIMAL1("images/animal-1.png"),
    ANIMAL2("images/animal-2.png"),
    ANIMAL3("images/animal-3.png"),
    ANIMAL4("images/animal-4.png"),
    ANIMAL5("images/animal-5.png"),
    ANIMAL6("images/animal-6.png"),
    ANIMAL7("images/animal-7.png"),
    ANIMAL8("images/animal-8.png"),
    ANIMAL9("images/animal-9.png"),
    ANIMAL10("images/animal-10.png");

    private final String imagePath;

    AnimalTile(String imagePath){
        this.imagePath = imagePath;
    }

    public Image getImage(){
        return new Image(this.imagePath);
    }
}
