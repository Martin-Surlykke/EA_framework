package com.ea_framework.Algorithms;

import java.util.Random;

public class twoOpt_tsp {

    public static int [][] twoOpt (int [][] input, double [][] distanceMatrix) {

        int[][] neighbor = createNeighbour(input);

        double cost_neighbor = costFunction(neighbor, distanceMatrix);
        double cost_input = costFunction(input, distanceMatrix);

        double delta = cost_neighbor - cost_input;

        if (delta < 0) {
            input = neighbor;
        }

        return input;

    }

    public static double getDelta(int [][] input1, int [][] input2, double [][] distanceMatrix) {
        double delta;

        double cost_input1 = costFunction(input1, distanceMatrix);
        double cost_input2 = costFunction(input2, distanceMatrix);



        delta = cost_input2 - cost_input1;

        return delta;

    }

    private static boolean edgesHaveCommonVertex(int[] edge1, int[] edge2) {
        return edge1[0] == edge2[0] || edge1[0] == edge2[1] || edge1[1] == edge2[0] || edge1[1] == edge2[1];
    }


    public static int[][] createNeighbour(int[][] input) {

        int[][] output = deepCopyList(input);
        Random rand = new Random();

        // Randomly pick two distinct edges (i, j) and (k, l)
        int i = rand.nextInt(input.length);
        int j = rand.nextInt(input.length);

        // Ensure i and j are distinct and do not refer to the same edge
        while (i == j || edgesHaveCommonVertex(input[i], input[j])) {
            j = rand.nextInt(input.length);
        }

        if (i > j) {
            int temp = i;
            i = j;
            j = temp;
        }

        int temp = output[j][0];
        output[j][0] = output[i][1];
        output[i][1] = temp;

        swap(output, i+1, j-1);
        return output;
    }

    public static void swap(int[][] input, int x, int y) {

        for (int i = x; i <= y; i++) {
            int temp = input[i][0];
            input[i][0] = input[i][1];
            input[i][1] = temp;
        }

        while (x < y) {
            // Swap the elements at start and end
            int [] temp = input[x];
            input[x] = input[y];
            input[y] = temp;

            // Move the pointers towards the center
            x++;
            y--;
        }
    }


    public static double costFunction (int [][] list, double[][] distanceMatrix){
        double cost = 0;

        for (int[] integers : list) {

            cost += distanceMatrix[integers[0] - 1][integers[1] - 1];
        }
        return cost;
    }

    public static int [][] deepCopyList(int [][] list){
        int [][] copy = new int[list.length][2];
        for (int i = 0; i < list.length; i++){
            copy[i][0] = list[i][0];
            copy[i][1] = list[i][1];
        }
        return copy;
    }
}
