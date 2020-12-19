package simulation;

import java.util.Comparator;

public class AnimalEnergyComparator implements Comparator<Animal> {

    @Override
    public int compare(Animal a1, Animal a2) {
        return Double.compare(a1.getEnergy(), a2.getEnergy());
    }

}
