package com.etoullali;

import java.util.ArrayList;
import java.util.List;

public class GAApplication {

    public static void main(String[] args) {
        GeniticAlgorithm ga=new GeniticAlgorithm();
        ga.initialize();
        ga.sortPopulation();
        ga.showPopulation();
    }
}
