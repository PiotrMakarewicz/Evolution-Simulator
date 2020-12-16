package simulation;

public class Jungle {
    private final int x1;
    private final int x2;
    private final int y1;
    private final int y2;
    Jungle(int boardWidth, int boardHeight, double jungleratio){
        this.x1 = (int) Math.round((1.0 - jungleratio)*0.5*boardWidth);
        this.x2 = (int) Math.round((1.0 + jungleratio)*0.5*boardWidth);
        this.y1 = (int) Math.round((1.0 - jungleratio)*0.5*boardHeight);
        this.y2 = (int) Math.round((1.0 + jungleratio)*0.5*boardWidth);
    }

    Jungle(int x1, int y1, int x2, int y2) throws InvalidRectangleException{
        if (x2 < x1 || y2 < y1) throw new InvalidRectangleException("Trying to create an invalid rectangle in jungle constructor: \n"
                + "x1 = " + x1
                + ", y1 = " + y1
                + ", x2 = " + x2
                + ", y2 = " + y2
            );
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
    }
    public boolean contains(int x, int y){
        return x1 <= x && x <= x2 && y1 <= y && y <= y2;
    }

    public boolean contains(Location location){
        return contains(location.getX(), location.getY());
    }

    public Location getRandomLocation() throws InvalidRectangleException{
        return Location.getRandom(x1,y1,x2+1,y2+1);
    }

    public int getX1() {
        return x1;
    }

    public int getX2() {
        return x2;
    }

    public int getY1() {
        return y1;
    }

    public int getY2() {
        return y2;
    }

    public Location getTopLeftCorner(){
        return new Location(x1,y1);
    }

    public Location getBottomRightCorner(){
        return new Location(x2,y2);
    }
}
