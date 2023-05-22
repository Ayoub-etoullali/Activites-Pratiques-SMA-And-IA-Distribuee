package com.etoullali;

import java.util.Random;

public class Individual implements Comparable {
    int[] chromosome = new int[GAUtils.CHROMOSOME_SIZE];
    int fitness;

    public Individual() {
        Random random = new Random();
        for (int i = 0; i < GAUtils.CHROMOSOME_SIZE; i++) {
            chromosome[i] = random.nextInt(2);
        }
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public int calculateFitness() {
        for (int gene : chromosome) {
            fitness += gene;
        }
        return fitness;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual = (Individual) o;
        if (this.fitness > individual.fitness) return 1;
        else if (this.fitness < individual.fitness) return -1;
        else return 0;
    }
}
