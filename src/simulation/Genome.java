package simulation;

import java.util.*;
import java.util.stream.Collectors;

public class Genome {
    private final List<Integer> genes;
    Genome(){
        Random rng = new Random();
        List<Integer> genes;
        do {
            genes = new ArrayList<>();
            for (int i = 0; i < 32; i++) {
                genes.add(rng.nextInt(8));
            }
        } while (! containsAllGenes(genes));
        this.genes = genes.stream().sorted().collect(Collectors.toList());
    };

    Genome(Genome g0, Genome g1){
        Random rng = new Random(System.nanoTime());
        List<Integer> genes = new ArrayList<Integer>();
        List<Genome> parentGenomes = new ArrayList<Genome>();
        parentGenomes.add(g0);
        parentGenomes.add(g1);
        int breakpt1 = rng.nextInt(31);
        int breakpt2 = breakpt1 + 1 + rng.nextInt(31-breakpt1);
        int firstPartFrom = rng.nextInt(2);
        int secondPartFrom = rng.nextInt(2);
        int thirdPartFrom = firstPartFrom == secondPartFrom ? (firstPartFrom ^ 1) & 1 : rng.nextInt(2);

        genes.addAll(parentGenomes.get(firstPartFrom).genes.subList(0,breakpt1));
        genes.addAll(parentGenomes.get(secondPartFrom).genes.subList(breakpt1,breakpt2));
        genes.addAll(parentGenomes.get(thirdPartFrom).genes.subList(breakpt2,32));
        this.genes = Genome.addMissingGenes(genes).stream().sorted().collect(Collectors.toList());
    };

    private static List<Integer> addMissingGenes(List<Integer> genes){
        while (getMissingGene(genes) != null){
            AbstractSet<Integer> occurringGenes = new TreeSet<Integer>();
            AbstractSet<Integer> repeatingGenes = new TreeSet<Integer>();
            List<Integer> repeatingGeneIndices = new ArrayList<Integer>();
            for (Integer gene : genes){
                if(occurringGenes.contains(gene)){
                    repeatingGenes.add(gene);
                }
                occurringGenes.add(gene);
            }
            for (int i = 0; i < genes.size(); i++){
                if (repeatingGenes.contains(genes.get(i))){
                    repeatingGeneIndices.add(i);
                }
            }
            Random rng = new Random(System.nanoTime());
            int i = rng.nextInt(repeatingGeneIndices.size());
            genes.set(repeatingGeneIndices.get(i),getMissingGene(genes));
        }
        return genes;
    }

    public int pickRandomGene(){
        int i = new Random().nextInt(genes.size());
        return genes.get(i);
    }

    public static boolean containsAllGenes(List<Integer> genes){
        return getMissingGene(genes) == null;
    }

    public static Integer getMissingGene(List<Integer> genes){
        int bitMask = 0; // bitwise 00000000
        for (int gene : genes) bitMask |= (1 << gene);
        for (int i = 0; i < 8; i++){
            if (((bitMask >> i) & 1) == 0){
                return i;
            }
        }
        return null;
    }
    public static boolean genesOrdered(List<Integer> genes){
        for (int i = 0; i + 1 < genes.size(); i++){
            if(genes.get(i) > genes.get(i+1))
                return false;
        }
        return true;
    }

    public boolean isValid(){
        return Genome.containsAllGenes(this.genes) && Genome.genesOrdered(this.genes);
    }

    public List<Integer> getGenes(){
        return List.copyOf(genes);
    }
}
