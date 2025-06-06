package com.ea_framework.Configs;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Problems.BitStringCompatible;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringACOConfig implements AlgorithmConfig {

    // Configuration for Ant Colony Optimization (ACO) for Bit String Problems

    // Parameters for ACO
    private int numAnts;
    private double evaporationRate;
    private double initialPheromone;
    private double reinforcement;
    private BitStringACOType acoType;
    private Fitness fitness;


    // Constructor to initialize the ACO configuration with default values
    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.numAnts = (int) raw.get("numAnts");
        this.evaporationRate = (double) raw.get("evaporationRate");
        this.initialPheromone = (double) raw.get("initialPheromone");
        this.reinforcement = (double) raw.get("reinforcement");
        this.acoType = (BitStringACOType) raw.get("type");

        // Due to the structure of bitstring problems, fitness is drawn directly from the problem

        this.fitness = ((BitStringCompatible) problem).getFitnessFunction();
    }

    // Getters for the ACO parameters
    public int numAnts() { return numAnts; }
    public double evaporationRate() { return evaporationRate; }
    public double initialPheromone() { return initialPheromone; }
    public double reinforcement() { return reinforcement; }
    public BitStringACOType acoType() { return acoType; }

    public Fitness fitness() {
        return fitness;
    }
}

