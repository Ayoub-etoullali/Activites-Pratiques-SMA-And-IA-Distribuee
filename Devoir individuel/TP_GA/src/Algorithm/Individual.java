package Algorithm;

import java.util.Arrays;

public class Individual implements Comparable {
    GenticAlgorithm genticAlgorithm=new GenticAlgorithm();
    private String[] chromosome = new String[GAUtils.CHROMOSOME_SIZE];
    private int fitness;

    public Individual() {
        fitness = 0;
        for (int j = 0; j < GAUtils.CHROMOSOME_SIZE; j++) {
            chromosome[j] = genticAlgorithm.character();
        }
    }

    public Individual(String[] chromosome) {
        this.chromosome = Arrays.copyOf(chromosome, GAUtils.CHROMOSOME_SIZE);
    }

    public int calculateFitness() {
        int cpt = 0;
        for (String gene : chromosome) {
            if (gene.equals("E") && cpt == 0) fitness++;
            if (gene.equals("n") && cpt == 1) fitness++;
            if (gene.equals("j") && cpt == 2) fitness++;
            if (gene.equals("o") && cpt == 3) fitness++;
            if (gene.equals("y") && cpt == 4) fitness++;
            if (gene.equals(" ") && cpt == 5) fitness++;
            if (gene.equals("w") && cpt == 6) fitness++;
            if (gene.equals("i") && cpt == 7) fitness++;
            if (gene.equals("t") && cpt == 8) fitness++;
            if (gene.equals("h") && cpt == 9) fitness++;
            if (gene.equals(" ") && cpt == 10) fitness++;
            if (gene.equals("A") && cpt == 11) fitness++;
            if (gene.equals("y") && cpt == 12) fitness++;
            if (gene.equals("o") && cpt == 13) fitness++;
            if (gene.equals("u") && cpt == 14) fitness++;
            if (gene.equals("b") && cpt == 15) fitness++;
            cpt++;
        }
        return fitness;

    }

    public int getFitness() {
        return fitness;
    }

    public String[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(String[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if (this.fitness > individual.fitness) {
            return 1;
        } else if (this.fitness < individual.fitness) {
            return -1;
        } else {
            return 0;
        }
    }
}
