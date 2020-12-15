package simulation;

public class BoardParams {
    private final int width;
    private final int height;
    private final double jungleRatio;
    private final double moveEnergy;
    private final double plantEnergy;
    private final int initialAnimalsNum;

    public BoardParams(int width, int height, double jungleRatio, double moveEnergy, double plantEnergy, int initialAnimalsNum) {
        this.width = width;
        this.height = height;
        this.jungleRatio = jungleRatio;
        this.moveEnergy = moveEnergy;
        this.plantEnergy = plantEnergy;
        this.initialAnimalsNum = initialAnimalsNum;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public double getJungleRatio() {
        return jungleRatio;
    }

    public double getMoveEnergy() {
        return moveEnergy;
    }

    public double getPlantEnergy() {
        return plantEnergy;
    }

    public int getInitialAnimalsNum() {
        return initialAnimalsNum;
    }
}
