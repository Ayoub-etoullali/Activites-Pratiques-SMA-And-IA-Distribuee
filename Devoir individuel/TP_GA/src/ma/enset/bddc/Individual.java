package ma.enset.bddc;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class Individual implements Comparable {
    GenticAlgorithm genticAlgorithm=new GenticAlgorithm();
    /** 1 ou 0 **/
    //private int []chromosome=new int[GAUtils.CHROMOSOME_SIZE];
    /**
     * character
     **/
    private String[] chromosome = new String[GAUtils.CHROMOSOME_SIZE];
    private int fitness;

    public Individual() {
        fitness = 0;
        for (int j = 0; j < GAUtils.CHROMOSOME_SIZE; j++) {
            /** 1 ou 0 **/
            //chromosome[i]= random.nextInt(2);
            /** character **/
            /*   => V1
            int leftLimit = 97; // letter 'a'
            int rightLimit = 122; // letter 'z'
            int targetStringLength = 10;
            Random random = new Random();
            StringBuilder buffer = new StringBuilder(targetStringLength);
            for (int i = 0; i < 1; i++) {
                int randomLimitedInt = leftLimit + (int)
                        (random.nextFloat() * (rightLimit - leftLimit + 1));
                buffer.append((char) randomLimitedInt);
            }
            String generatedString = buffer.toString();
            System.out.println(" :"+ generatedString);
            generatedString+=" ";
            System.out.println(generatedString.toString());
*/
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
