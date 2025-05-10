package com.ea_framework.MutationFunctions;

import java.util.Random;

public class TSP2DTwoOpt implements MutationOperator<int[]> {

    @Override
    public int [] mutate(int[] solution) {
        return createNeighbour(solution);

    }

        public static int[] createNeighbour (int[] input){

            int[] output = deepCopyList(input);
            Random rand = new Random();

            // Randomly pick two distinct edges (i, j) and (k, l)
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
            swap(output, i+1, j);
            return output;
        }

        public static void swap ( int[] input, int x, int y){
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

        public static int[] deepCopyList ( int[] list){
            int[] copy = new int[list.length];
            System.arraycopy(list, 0, copy, 0, list.length);
            return copy;
        }

}
