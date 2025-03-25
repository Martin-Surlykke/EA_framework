package com.ea_framework.Algorithms;

public class RLS_tsp {

    public static int [][] run_RLS (int [][] input, double [][] distanceMatrix, int iterations) {
        int [][] current = input;
        int i = 0;
        while (i < iterations) {

            int [][] neighbour = twoOpt_tsp.twoOpt(input, distanceMatrix);
            double delta = twoOpt_tsp.getDelta(current, neighbour, distanceMatrix);

            if (delta < 0) {
                current = neighbour;
            }

            i++;
        }

        return current;

    }

}
