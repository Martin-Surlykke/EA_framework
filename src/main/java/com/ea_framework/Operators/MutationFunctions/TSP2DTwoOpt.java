package com.ea_framework.Operators.MutationFunctions;

import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.Configurable;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Problems.TSP2DProblem;

import java.util.Map;
import java.util.Random;

public class TSP2DTwoOpt implements MutationOperator, Configurable {

    @Override
    public Object mutate(Object solution) {
        if (!(solution instanceof int[] input)) {
            throw new IllegalArgumentException("Expected int[] for TSP2DTwoOpt");
        }

        return createNeighbour(input);
    }

    public static int[] createNeighbour(int[] input) {
        int[] output = deepCopyList(input);
        Random rand = new Random();

        int i = rand.nextInt(input.length);
        int j = rand.nextInt(input.length);

        while (i == j || areAdjacent(i, j, input.length)) {
            j = rand.nextInt(input.length);
        }

        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }

        swap(output, i + 1, j);
        return output;
    }

    public static void swap(int[] input, int x, int y) {
        while (x < y) {
            int temp = input[x];
            input[x] = input[y];
            input[y] = temp;
            x++;
            y--;
        }
    }

    public static boolean areAdjacent(int i, int j, int n) {
        return Math.abs(i - j) == 1 || (i == 0 && j == n - 1) || (j == 0 && i == n - 1);
    }

    public static int[] deepCopyList(int[] list) {
        int[] copy = new int[list.length];
        System.arraycopy(list, 0, copy, 0, list.length);
        return copy;
    }

    @Override
    public void configure(Map<String, Object> config) {

    }
}
