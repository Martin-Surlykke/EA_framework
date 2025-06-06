package com.ea_framework.Algorithms;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BitStringACOConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;

import java.util.*;

public class BitStringACO implements Algorithm {

    private double[][] pheromone;
    private int bitLength;
    private int numAnts;
    private double evaporationRate;
    private double initialPheromone;
    private final Random random = new Random();
    private Fitness fitnessFunction;
    private boolean[] currentSolution;
    private double currentFitness;
    private double bestFitness = Double.NEGATIVE_INFINITY;
    private int bestIteration = 0;
    private BitStringACOType acoType;

    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof BitStringACOConfig aco)) {
            throw new IllegalArgumentException("Expected BitStringACOConfig");
        }

        this.numAnts = aco.numAnts();
        this.evaporationRate = aco.evaporationRate();
        this.initialPheromone = aco.initialPheromone();
        this.acoType = aco.acoType();
        this.fitnessFunction = aco.fitness();
        this.acoType.setEvaporationRate(evaporationRate);
    }

    @Override
    public void setCurrentSolution(Object solution) {
        if (!(solution instanceof boolean[] bits)) {
            throw new IllegalArgumentException("Expected boolean[] for BitStringACO");
        }

        this.currentSolution = bits;
        this.bitLength = bits.length;
        this.pheromone = new double[bitLength][2];
        acoType.initializePheromones(pheromone, bitLength, initialPheromone);
    }

    @Override
    public Object getCurrentSolution() {
        return currentSolution;
    }

    @Override
    public Double getCurrentFitness() {
        return currentFitness;
    }

    @Override
    public Double getBestFitness() {
        return bestFitness;
    }

    @Override
    public int getBestIteration() {
        return bestIteration;
    }

    @Override
    public void run(int iteration) {
        boolean[] bestLocal = null;
        double bestLocalFitness = Double.NEGATIVE_INFINITY;

        for (int k = 0; k < numAnts; k++) {
            boolean[] candidate = constructSolution();
            double fitness = evalFitness(candidate);

            if (fitness > bestLocalFitness) {
                bestLocalFitness = fitness;
                bestLocal = candidate;
            }
        }

        updatePheromones(bestLocal, bestLocalFitness);
        currentSolution = bestLocal;
        currentFitness = bestLocalFitness;

        if (bestLocalFitness > bestFitness) {
            bestFitness = bestLocalFitness;
            bestIteration = iteration;
        }
    }

    private boolean[] constructSolution() {
        boolean[] solution = new boolean[bitLength];
        for (int i = 0; i < bitLength; i++) {
            double p0 = pheromone[i][0];
            double p1 = pheromone[i][1];
            double prob1 = p1 / (p0 + p1);
            solution[i] = random.nextDouble() < prob1;
        }
        return solution;
    }

    private void updatePheromones(boolean[] best, double fitness) {
        acoType.updatePheromones(pheromone, best, fitness);
    }

    private double evalFitness(boolean[] bits) {
        Object result = fitnessFunction.evaluate(bits);
        if (!(result instanceof Number n)) {
            throw new IllegalStateException("Fitness must return a number");
        }
        return n.doubleValue();
    }
}
