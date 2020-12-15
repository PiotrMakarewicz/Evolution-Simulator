package simulation;

public class Location implements Comparable<Location>{
    private final int x;
    private final int y;

    Location(int x,int y){
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
}
