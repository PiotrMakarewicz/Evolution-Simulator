package simulation;

import java.util.ArrayList;
import java.util.List;

public class GenomeWatcher{
    AnimalBoard animalBoard;
    private List<Genome> genomeFrequency = new ArrayList<Genome>();

    public void update(){};
    public Genome getDominantGenome(){return genomeFrequency.get(0);};
    public List<Animal> getAnimalsWithDominantGenome(){return null;};
};
