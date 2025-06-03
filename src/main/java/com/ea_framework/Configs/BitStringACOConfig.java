package com.ea_framework.Configs;

import com.ea_framework.ACOTypes.BitStringACOType;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Problems.BitStringCompatible;
import com.ea_framework.Problems.Problem;

import java.util.Map;

public class BitStringACOConfig implements AlgorithmConfig {
    private int numAnts;
    private double evaporationRate;
    private double initialPheromone;
    private double reinforcement;
    private BitStringACOType acoType;
    private Fitness fitness;

    @Override
    public void populate(Map<String, Object> raw, Problem problem) {
        this.numAnts = (int) raw.get("numAnts");
        this.evaporationRate = (double) raw.get("evaporationRate");
        this.initialPheromone = (double) raw.get("initialPheromone");
        this.reinforcement = (double) raw.get("reinforcement");
        this.acoType = (BitStringACOType) raw.get("type");

        this.fitness = ((BitStringCompatible) problem).getFitnessFunction();
    }

    public int numAnts() { return numAnts; }
    public double evaporationRate() { return evaporationRate; }
    public double initialPheromone() { return initialPheromone; }
    public double reinforcement() { return reinforcement; }
    public BitStringACOType acoType() { return acoType; }

    public Fitness fitness() {
        return fitness;
    }
}

