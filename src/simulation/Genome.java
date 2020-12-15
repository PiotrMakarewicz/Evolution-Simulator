package simulation;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Genome {
    private final List<Integer> genes;
    private int length;

    Genome(int length){
        Random rng = new Random();
        List<Integer> genes;
        do {
            genes = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                genes.add(rng.nextInt(8));
            }
        } while (! this.isValid());
        this.genes = (List<Integer>) genes.stream().sorted();
        this.length = length;
    };

    Genome(Genome g1, Genome g2){
        Random rng = new Random();
        List<Integer> genes = new ArrayList<>();
        int breakpt1 = rng.nextInt(31);
        int breakpt2 = breakpt1 + 1 + rng.nextInt(31-breakpt1);

        this.genes = genes;
    };

    public int pickRandomGene(){
        int i = new Random().nextInt(genes.size());
        return genes.get(i);
    }

    public boolean isValid(){
        // checks if the genome contains all 8 genes
        int bitMask = 0;
        for (int gene : genes) bitMask |= (1 << gene);
        return bitMask == (2^8 - 1);
    }
}
