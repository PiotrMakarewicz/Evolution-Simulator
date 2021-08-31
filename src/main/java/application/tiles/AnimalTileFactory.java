package application.tiles;

import simulation.Animal;
import simulation.AnimalEnergyComparator;
import simulation.Simulation;

public class AnimalTileFactory{
    private final Simulation simulation;
    public AnimalTileFactory(Simulation simulation){
        this.simulation = simulation;
    }
    public AnimalTile getTile(int x, int y){
        if (simulation.animalBoard.noAnimalsAt(x,y))
            return null;
        else {
            Animal animal = simulation.animalBoard.get(x,y).stream().max(new AnimalEnergyComparator()).get();
            double energy = animal.getEnergy();
            if (energy < 10) return AnimalTile.ANIMAL1;
            if (energy < 20) return AnimalTile.ANIMAL2;
            if (energy < 30) return AnimalTile.ANIMAL3;
            if (energy < 40) return AnimalTile.ANIMAL4;
            if (energy < 50) return AnimalTile.ANIMAL5;
            if (energy < 60) return AnimalTile.ANIMAL6;
            if (energy < 70) return AnimalTile.ANIMAL7;
            if (energy < 80) return AnimalTile.ANIMAL8;
            if (energy < 90) return AnimalTile.ANIMAL9;
            else return AnimalTile.ANIMAL10;
        }
    }
}

