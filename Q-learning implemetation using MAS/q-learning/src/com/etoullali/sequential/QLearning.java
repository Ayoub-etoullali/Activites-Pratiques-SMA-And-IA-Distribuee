package com.etoullali.sequential;

import java.util.Arrays;
import java.util.Random;

public class QLearning {
    private final double ALPHA = 0.1; //learning rate, determines to what extent newly acquired information overrides old information
    private final double GAMMA = 0.9; //discount rate, determines the importance of future rewards
    private final double EPS = 0.4; //epsilon greedy, determines the probability of taking a random action, rather than action that gives a maximum value of Q
    private final int MAX_EPOCH = 300;
    private final int GRID_SIZE = 3;
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
        this.grid = new int[][]{
                {0, 0, 1},
                {0, -1, 0},
                {0, 0, 0}
        };
        this.actions = new int[][]{
                {0, -1}, //gouche
                {0, 1}, //droite
                {1, 0}, //bas
                {-1, 0} //haut
        };
    }

    private void resetState() {
        stateI = 2;
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
        stateI = Math.max(0, Math.min(actions[act][0] + stateI, 2)); // pour éviter if else
        stateJ = Math.max(0, Math.min(actions[act][1] + stateJ, 2));
        return stateI * GRID_SIZE + stateJ;
    }

    private boolean finished() {
        return grid[stateI][stateJ] == 1;
    }

    private void showResult() {
        System.out.println("************************ Q Table ************************");
        for (double line[] : qTable) {
            System.out.print("[");
            for (double qValue : line) {
                System.out.print(qValue+",");
            }
            System.out.println("]");
        }

        System.out.println("");

        System.out.println("************************ Exploration ************************");
        resetState();
        while (!finished()) {
            int act = chooseAction(0); // faire juste l'exploitation
            System.out.println("State : "+(stateI*GRID_SIZE+stateJ)+"\t  Action : "+act);
            executeAction(act);
        }
        System.out.println("Final state : "+(stateI*GRID_SIZE+stateJ));
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
