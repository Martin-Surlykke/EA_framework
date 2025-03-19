package com.ea_framework.Algorithms;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class AntColonyOptimization {

    static double[][] tau;

    static double rho = 0.1;// evaporation rate

    static double tauMax;

    static double tauMin;
    static double[][] eta; // heuristic information matrix

    public static int [][] ACO (double [][] distanceMatrix, int [] nodes, int iterations) {
        tau = new double[nodes.length][nodes.length];
        eta = new double[nodes.length][nodes.length];

        tauMax = 1-(1.0/nodes.length);
        tauMin = 1/Math.pow(nodes.length,2);

        double alpha = 1.5;
        double beta = 2.5;

        int [] tour = MMAS(distanceMatrix, alpha, beta, nodes, iterations);

        return transFormEdgeList(tour);
    }

    public static int [] MMAS(double[][] distanceMatrix, double alpha, double beta, int[] V, int iterations) {

        initTau(V.length);
        initEta(distanceMatrix, V.length);



        int [] tour = construct(V, tau, eta, alpha, beta) ;
        int [] potential;
        update(tour);

        int x = 0;
        while (x < iterations) {
            potential = construct(V, tau, eta, alpha, beta) ;
            if (isBetter(tour,potential, distanceMatrix)) {
                tour = potential;
            }
            update(tour);
            x++;

        }


        return tour;


    }

    private static boolean isBetter(int[] tour, int[] potential, double[][] distanceMatrix) {
        double d_tour = 0.0;
        double d_potential = 0.0;
        for (int i = 1; i < tour.length; i++) {
            d_tour += distanceMatrix[tour[i-1]][tour[i]];
            d_potential += distanceMatrix[potential[i-1]][potential[i]];
        }

        d_tour += distanceMatrix[tour[tour.length-1]][tour[0]];
        d_potential += distanceMatrix[potential[potential.length-1]][potential[0]];

        return d_potential < d_tour;
    }

    public static int[] construct(int[] nodes, double[][] pheromones, double[][] heuristic, double alpha, double beta) {

        int [] construct = new int[nodes.length];

        Random rand = new Random();
        int x = rand.nextInt(nodes.length);

        int currentNode = nodes[x];

        construct[0] = currentNode;

        List<Integer> unvisited = new ArrayList<Integer>();
        for (int node : nodes) {
            unvisited.add(node);
        }
        unvisited.remove(nodes[x]);

        for (int i = 1; i < nodes.length; i++) {
            double R = calculateR(currentNode, pheromones, heuristic, alpha, beta, unvisited);
            currentNode = getNextNode(currentNode, R, tau, eta, unvisited, alpha, beta);
            unvisited.remove(Integer.valueOf(currentNode));
            construct[i] = currentNode;
        }

        return construct;
    }

    public static void update(int [] tour) {

        for (int i = 0; i < tour.length - 1; i++) {
            int currentNode = tour[i];
            int nextNode = tour[i+1];

            tau[currentNode][nextNode] +=
                    Math.min((1-rho)*tau[currentNode][nextNode] + rho, tauMax);
            tau[nextNode][currentNode] = tau[currentNode][nextNode];

        }

        int lastNode = tour[tour.length-1];
        int firstNode = tour[0];

        tau[lastNode][firstNode] = Math.min((1-rho)*tau[lastNode][firstNode] + rho,
                      tauMax);
        tau[firstNode][lastNode] = tau[lastNode][firstNode];

        for (int i = 0; i < tau.length; i++) {
            for (int j = 0; j < tau.length; j++) {
                if (!inTour(i,j,tour)) {
                    tau[i][j] = Math.max((1-rho) * tau[i][j], tauMin);
                }
            }
        }
    }

    private static boolean inTour(int i, int j, int[] tour) {
        for (int k = 0; k < tour.length-1; k++) {
            if ((tour[k] == i && tour[k+1] == j) || (tour[k] == j && tour[k+1] == i)) {
                return true;
            }
        }
        return tour[tour.length - 1] == i && tour[0] == j || (tour[tour.length - 1] == j && tour[0] == i);
    }

    public static int getNextNode(int currentNode, double R, double [][] pheromones, double [][] heuristic, List<Integer> unvisited, double alpha, double beta) {
        double P = 0.0;

        double [] cumulativeProbs = new double[unvisited.size()];

        for (int i = 0; i < unvisited.size(); i++) {
            int node = unvisited.get(i);
            double a = Math.pow(pheromones[currentNode][node],alpha);
            double b = Math.pow(heuristic[currentNode][node],beta);

            P += (a*b)/R;
            cumulativeProbs[i] = P;
        }

        double rand = Math.random();

        for (int i = 0; i < cumulativeProbs.length; i++) {
            if (rand <= cumulativeProbs[i]) {
                return unvisited.get(i);
            }
        }
        return -1;
    }


    public static double calculateR(int currentNode, double[][] pheromones, double[][] heuristic, double alpha, double beta, List<Integer> unvisitedNotes) {
        double R = 0.0;

        for (int node : unvisitedNotes) {
            double x = Math.pow(pheromones[currentNode][node], alpha);
            double y = Math.pow(heuristic[currentNode][node], beta);

            R += x * y;
        }

        return R;
    }


    private static int[][] transFormEdgeList(int [] tour) {
        int [][] edgeList = new int[tour.length][2];

        for (int i = 0; i < tour.length-1; i++) {
            edgeList[i][0] = tour[i]+1;
            edgeList[i][1] = tour[i+1]+1;
        }
        edgeList[tour.length-1][0] = tour[tour.length-1]+1;
        edgeList[tour.length-1][1] = tour[0]+1;

        return edgeList;
    }


    private static void initTau(int n) {

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tau[i][j] = 1 / ((double) n);
            }
        }
    }


    private static void initEta(double[][] distanceMatrix, int n) {


        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (distanceMatrix[i][j] == 0.0) {
                    eta[i][j] = 0.0;
                }
                else {
                    eta[i][j] = 1.0 / distanceMatrix[i][j];
                }
                }
        }


    }

}
