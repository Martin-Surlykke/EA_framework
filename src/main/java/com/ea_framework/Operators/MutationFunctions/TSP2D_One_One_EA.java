package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;

import java.util.Random;

public class TSP2D_One_One_EA implements MutationOperator, Configurable {

    private double lambda = 1.0;
    private final Random random = new Random();

    @Override
    public Object mutate(Object solution) {
        if (!(solution instanceof int[] input)) {
            throw new IllegalArgumentException("Expected int[] for PoissonSwapMutation");
        }

        int[] output = deepCopy(input);
        int k = samplePoisson(lambda);

        for (int i = 0; i < k; i++) {
            int a = random.nextInt(output.length);
            int b = random.nextInt(output.length);
            int temp = output[a];
            output[a] = output[b];
            output[b] = temp;
        }

        return output;
    }

    private int samplePoisson(double lambda) {
        double L = Math.exp(-lambda);
        int k = 0;
        double p = 1.0;
        do {
            k++;
            p *= random.nextDouble();
        } while (p > L);
        return k - 1;
    }

    private int[] deepCopy(int[] array) {
        int[] copy = new int[array.length];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }

    public void setLambda(double lambda) {
        this.lambda = lambda;
    }

    @Override
    public void configure(Problem problem) {
        // You could add logic here to customize λ per problem instance, if needed
    }
}
