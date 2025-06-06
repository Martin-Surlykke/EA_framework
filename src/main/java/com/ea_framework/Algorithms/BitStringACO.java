package com.ea_framework.Algorithms;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BitStringACOConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;

import java.util.*;

public class BitStringACO implements Algorithm {

    // Ant colony optimization for bit strings
    // This implementation uses a pheromone matrix to guide the search for optimal bit strings.
    private double[][] pheromone;

    // Length of the bit string, used to determine the size of the pheromone matrix.
    private int bitLength;

    // Number of ants in the colony, which determines how many solutions are constructed per iteration.
    private int numAnts;

    // Evaporation rate of pheromones, which controls how quickly pheromones decay over time.
    private double evaporationRate;

    // Initial pheromone value, which is set for each bit position at the start of the algorithm.
    private double initialPheromone;

    // Reinforcement value, which is added to the pheromone trails based on the fitness of the solutions found.
    private double reinforcement;

    private final Random random = new Random();

    private Fitness fitnessFunction;

    private boolean[] currentSolution;

    // Current fitness of the solution, used to track the quality of the current solution.
    private double currentFitness;

    // Best fitness found so far, used to track the best solution across iterations.
    private double bestFitness = Double.NEGATIVE_INFINITY;

    // Best iteration where the best fitness was found, used to track progress.
    private int bestIteration = 0;

    private BitStringACOType acoType;


    // Constructor to initialize the ACO algorithm with a specific type.
    @Override
    public void apply(AlgorithmConfig config) {
        if (!(config instanceof BitStringACOConfig aco)) {
            throw new IllegalArgumentException("Expected BitStringACOConfig");
        }

        this.numAnts = aco.numAnts();
        this.evaporationRate = aco.evaporationRate();
        this.initialPheromone = aco.initialPheromone();
        this.reinforcement = aco.reinforcement();
        this.acoType = aco.acoType();
        this.fitnessFunction = aco.fitness();
    }


    // Sets the current solution for the ACO algorithm, which is expected to be a boolean array representing a bit string.
    @Override
    public void setCurrentSolution(Object solution) {
        if (!(solution instanceof boolean[] bits)) {
            throw new IllegalArgumentException("Expected boolean[] for BitStringACO");
        }

        this.currentSolution = bits;
        this.bitLength = bits.length;
        this.pheromone = new double[bitLength][2];

        pheromone = new double[bitLength][2];
        acoType.initializePheromones(pheromone, bitLength, initialPheromone);
    }


    // Getters and setters for the current solution, fitness, and best fitness found so far.
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


    // Runs the ACO algorithm for a specified number of iterations, constructing solutions and updating pheromones.
    @Override
    public void run(int iteration) {
        boolean[] bestLocal = null;
        double bestLocalFitness = Double.NEGATIVE_INFINITY;

        // Construct solutions using the ants in the colony
        for (int k = 0; k < numAnts; k++) {
            boolean[] candidate = constructSolution();
            double fitness = evalFitness(candidate);

            if (fitness > bestLocalFitness) {
                bestLocalFitness = fitness;
                bestLocal = candidate;
            }
        }

        // Update pheromones based on the best solution found by the ants
        updatePheromones(bestLocal);
        currentSolution = bestLocal;
        currentFitness = bestLocalFitness;

        // Log the best solution found so far and iteration
        if (bestLocalFitness > bestFitness) {
            bestFitness = bestLocalFitness;
            bestIteration = iteration;
        }
    }

    // Constructs a solution by probabilistically choosing bits based on pheromone levels.
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

    private void updatePheromones(boolean[] best) {
        acoType.updatePheromones(pheromone, best, evalFitness(best));
    }


    // Evaluates the fitness of a given bit string using the configured fitness function.
    // Uses a generic fitness function that returns a Number, which is then converted to a double.
    private double evalFitness(boolean[] bits) {
        Object result = fitnessFunction.evaluate(bits);
        if (!(result instanceof Number n)) {
            throw new IllegalStateException("Fitness must return a number");
        }
        return n.doubleValue();
    }
}
