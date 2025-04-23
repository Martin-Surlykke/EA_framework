package com.ea_framework.Mutation;

import java.util.Random;

public class TwoOptTsp implements MutationOperator<int[]> {

    @Override
    public int [] mutate(int[] solution) {
        return createNeighbour(solution);

    }

    public static int[] getNeighbours(int[] list, int index) {
        int left, right;

        if (index == list.length - 1) {
            right = 0;
            left = index - 1;
        } else if (index == 0) {
            left = list.length - 1;
            right = index + 1;
        } else {
            left = index - 1;
            right = index + 1;
        }

        return new int[]{left, right};
    }

    private static boolean edgesHaveCommonVertex(int[] list, int index1, int index2) {
        int[] neighbors1 = getNeighbours(list, index1);
        int[] neighbors2 = getNeighbours(list, index2);

        int[] nodes1 = {
                list[neighbors1[0]], // left
                list[index1],
                list[neighbors1[1]]  // right
        };

        int[] nodes2 = {
                list[neighbors2[0]],
                list[index2],
                list[neighbors2[1]]
        };

        for (int a : nodes1) {
            for (int b : nodes2) {
                if (a == b) {
                    return true;
                }
            }
        }

        return false;
    }


        public static int[] createNeighbour (int[] input){

            int[] output = deepCopyList(input);
            Random rand = new Random();

            // Randomly pick two distinct edges (i, j) and (k, l)
            int i = rand.nextInt(input.length);
            int j = rand.nextInt(input.length);

            // Ensure i and j are distinct and do not refer to the same edge
            while (i == j || edgesHaveCommonVertex(input, i, j)) {
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

        public static int[] deepCopyList ( int[] list){
            int[] copy = new int[list.length];
            System.arraycopy(list, 0, copy, 0, list.length);
            return copy;
        }

}
