package ma.enset.bddc;

import java.util.Arrays;
import java.util.Random;

public class Individual implements Comparable{
    private int []chromosome=new int[GAUtils.CHROMOSOME_SIZE];
    private int fitness;

    public Individual() {
        Random random=new Random();
        for (int i=0;i<GAUtils.CHROMOSOME_SIZE;i++) {
            chromosome[i]= random.nextInt(2);
        }
    }

    public Individual(int[] chromosome) {
        this.chromosome = Arrays.copyOf(chromosome,GAUtils.CHROMOSOME_SIZE);
    }

    public void calculateFintess(){
        for (int gene:chromosome) {
            fitness+=gene;
        }
    }

    public int getFitness() {
        return fitness;
    }

    public int[] getChromosome() {
        return chromosome;
    }

    public void setChromosome(int[] chromosome) {
        this.chromosome = chromosome;
    }

    @Override
    public int compareTo(Object o) {
        Individual individual=(Individual) o;
        if (this.fitness>individual.fitness){
            return  1;
        }else if(this.fitness< individual.fitness){
            return -1;
        }else{
            return 0;
        }
    }
}
