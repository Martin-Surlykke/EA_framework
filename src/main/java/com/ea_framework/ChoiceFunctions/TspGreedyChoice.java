package com.ea_framework.ChoiceFunctions;

public class TspGreedyChoice implements ChoiceFunction<int[][], Double> {

    @Override
    public int[][] choose(int[][] current, int[][] candidate, Double fitnessCurrent, Double fitnessCandidate, int iteration) {
        return fitnessCandidate > fitnessCurrent ? candidate : current;
    }
}