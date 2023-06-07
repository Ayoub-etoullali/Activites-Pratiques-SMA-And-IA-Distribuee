package com.etoullali.sequential;

import java.util.Arrays;
import java.util.Random;

public class QLearning {
    private final double ALPHA = 0.1; //learning rate, determines to what extent newly acquired information overrides old information
    private final double GAMMA = 0.9; //discount rate, determines the importance of future rewards
    private final double EPS = 0.4; //epsilon greedy, determines the probability of taking a random action, rather than action that gives a maximum value of Q
    private final int MAX_EPOCH = 90000;
    private final int GRID_SIZE = 5;
    private final int ACTION_SIZE = 4;
    private int[][] grid;
    private double[][] qTable = new double[GRID_SIZE * GRID_SIZE][ACTION_SIZE];
    private int[][] actions;
    private int stateI;
    private int stateJ;

    /**
     * Step 0) Episode Initialization :
     * The agent starts episode in the bottom left corner
     * <p>
     * => Repeat (for each state) :
     * Step 1) Agent chooses an action
     * Selects random action with probability ϵ or with probability 1−ϵ selects optimal action
     * Step 2) Agent takes an action :
     * Agent receives a reward signal from the environment and enters the next states
     * Step 3) Update Q value :
     * Q(s,a) = Q(s,a) + ALPHA ( r + GAMA * max[Q(s,a)] − Q(s,a) )
     */

    public QLearning() {
        /*this.grid = new int[][]{
                {0, 0, 1},
                {0, -1, 0},
                {0, 0, 0}
        };*/
        this.grid = new int[][]{
                {0, -1, 0, 0, 0},
                {0, 0, 0, -1, 0},
                {-1, -1, -1, 0, 0},
                {-1, 1, -1, 0, -1},
                {-1, 0, 0, 0, -1}
        };
        this.actions = new int[][]{
                {0, -1}, //Gauche
                {0, 1}, //Droit
                {1, 0}, //Bas
                {-1, 0} //Haut
        };
    }

    private void resetState() {
        stateI = 0;
        stateJ = 0;
    }

    private int chooseAction(double eps) {
        Random rn = new Random();
        double bestQ = 0;
        int act = 0;
        if (rn.nextDouble() < eps) {
            //exploration (nouvelles actions)
            act = rn.nextInt(ACTION_SIZE);
        } else {
            //exploitation (connaissances accumulées)
            int state = stateI * GRID_SIZE + stateJ; // matrix to list
            for (int i = 0; i < ACTION_SIZE; i++) {
                if (qTable[state][i] > bestQ) {
                    bestQ = qTable[state][i];
                    act = i;
                }
            }
        }
        return act;
    }

    private int executeAction(int act) {
        stateI = Math.max(0, Math.min(actions[act][0] + stateI, GRID_SIZE - 1)); // pour éviter if else
        stateJ = Math.max(0, Math.min(actions[act][1] + stateJ, GRID_SIZE - 1));
        return stateI * GRID_SIZE + stateJ;
    }

    private boolean finished() {
        return grid[stateI][stateJ] == 1;
    }

    private void showResult() {
        System.out.println("_____________________________________ Q Table _____________________________________");
        for (double line[] : qTable) {
            System.out.print("[");
            for (double qValue : line) {
                System.out.print(qValue + ",");
            }
            System.out.println("]");
        }
        System.out.println("______________________________________________________________________________________\n");

        System.out.println("_____________________________________ Exploration _____________________________________");
        resetState();
        while (!finished()) {
            int act = chooseAction(0); // faire juste l'exploitation
            System.out.println("State : " + (stateI * GRID_SIZE + stateJ) + "\t  Action : " + Action(act));
            executeAction(act);
        }
        System.out.println("______________________________________________________________________________________");
        System.out.println("Final state : " + (stateI * GRID_SIZE + stateJ));
        System.out.println("______________________________________________________________________________________");
    }

    private String Action(int act) {
        switch (act) {
            case 0:
                return "Gauche";
            case 1:
                return "Droit";
            case 2:
                return "Bas";
            case 3:
                return "Haut";
        }
        return null;
    }

    public void runQLearning() {
        int it = 0;
        int currentState;
        int nextState;
        int act, act1;
        resetState();
        while (it < MAX_EPOCH) {
            resetState();
            while (!finished()) {
                currentState = stateI * GRID_SIZE + stateJ;
                act = chooseAction(EPS);

                nextState = executeAction(act);
                act1 = chooseAction(0); // faire juste l'exploitation

                qTable[currentState][act] = qTable[currentState][act] + ALPHA * (grid[stateI][stateJ] + GAMMA * qTable[nextState][act1] - qTable[currentState][act]);
            }
            it++;
        }
        showResult();
    }


}
