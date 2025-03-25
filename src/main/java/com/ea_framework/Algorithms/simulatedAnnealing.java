package com.ea_framework.Algorithms;
import java.util.Random;

public class simulatedAnnealing {

    public static int [][] anneal(int [][] input, double alpha, double [][] distanceMatrix, int iterations) {

        double T = 1.0;
        int [][] current = input;
        int i = 0;

        while (i < iterations) {

            int [][] neighbor = twoOpt_tsp.twoOpt(current, distanceMatrix);

            double delta = twoOpt_tsp.getDelta(current, neighbor, distanceMatrix);

            if (delta < 0) {
                current = neighbor;
            } else {
                Random rand = new Random();
                double probability = Math.exp(-delta / T);
                double random = rand.nextDouble();

                if (random < probability) {
                    current = neighbor;
                }
            }

            T = (1-alpha)*T;
            i++;
        }



        return current;

    }

}
