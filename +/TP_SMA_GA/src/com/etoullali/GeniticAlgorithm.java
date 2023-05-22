package com.etoullali;

import java.util.*;

public class GeniticAlgorithm { //créer la 1ère génération des individus
    private Individual[] population=new Individual[GAUtils.POPULATION_SIZE];
    private Individual individual1;
    private Individual individual2;

    public void initialize(){
        for (int i=0;i<GAUtils.POPULATION_SIZE;i++){
            population[i]=new Individual();
        }
    }

//    public void selection(){
//        individual1=population[0];
//        individual2=population[1];
//    }

    public void crossover(){
        individual1=new Individual();
        individual2=new Individual();

        Random random=new Random();
        int crossPoint=random.nextInt(GAUtils.CHROMOSOME_SIZE);
        crossPoint++;
        for (int i = 0; i < crossPoint; i++) {
            individual1.getChromosome()[i]=population[1].getChromosome()[i];
            individual2.getChromosome()[i]=population[0].getChromosome()[i];
        }
    }
    public void showPopulation(){
        for (Individual individual:population) {
            System.out.println(Arrays.toString(individual.getChromosome()) +" = "+individual.calculateFitness());
        }
    }

    public void sortPopulation(){
        Arrays.sort(population);
//        Arrays.sort(population, Comparator.reverseOrder());
    }
}
