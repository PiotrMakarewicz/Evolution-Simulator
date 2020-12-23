package simulation;

import simulation.exceptions.InvalidRectangleException;

import java.util.Random;

public class Location implements Comparable<Location>{
    private final int x;
    private final int y;

    public Location(int x, int y){
        this.x = x;
        this.y = y;
    }

    @Override
    public int compareTo(Location o) {
        if (this.getX() == o.getX()) return this.getY() - o.getY();
        else return this.getX() - o.getX();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public static Location getRandom(int xBound, int yBound){
        Random rng = new Random(System.nanoTime());
        return new Location(rng.nextInt(xBound),rng.nextInt(yBound));
    }
    public static Location getRandom(int xLowerBound, int yLowerBound, int xUpperBound, int yUpperBound) throws InvalidRectangleException {
        if (xLowerBound >= xUpperBound || yLowerBound >= yUpperBound){
            throw new InvalidRectangleException(
                    "Trying to generate a random location from invalid parameters: \n"
                            + "xLowerBound = " + xLowerBound
                            + ", yLowerBound = " + yLowerBound
                            + ", xUpperBound = " + xUpperBound
                            + ", yUpperBound = " + yUpperBound
            );
        }

        Random rng = new Random(System.nanoTime());
        int x = xLowerBound + rng.nextInt(xUpperBound-xLowerBound);
        int y = yLowerBound + rng.nextInt(yUpperBound-yLowerBound);
        return new Location(x,y);
    }

    public Location stepTo(Direction direction){
        return new Location(x+ direction.getX(),y+direction.getY());
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }
}
