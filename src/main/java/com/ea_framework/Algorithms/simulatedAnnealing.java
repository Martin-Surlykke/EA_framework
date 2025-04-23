package com.ea_framework.Algorithms;
import com.ea_framework.Mutation.TwoOptTsp;

import java.util.Random;

public class simulatedAnnealing {

    public static int [][] anneal(int [][] input, double alpha, double [][] distanceMatrix, int iterations) {

        double T = 1.0;
        int [][] current = input;
        int i = 0;

        while (i < iterations) {

 /*           int [][] neighbor = TwoOptTsp.twoOpt(current, distanceMatrix);

            double delta = TwoOptTsp.getDelta(current, neighbor, distanceMatrix);

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
            */
        }



        return current;

    }

}
