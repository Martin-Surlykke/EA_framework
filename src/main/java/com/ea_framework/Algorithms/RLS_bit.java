package com.ea_framework.Algorithms;

import java.util.Random;

public class RLS_bit {

    public static int [] RLS_bitString (int n, int iterations, String type) {

        int [] current = createBitstring(n);
        int i = 0;

        while (i < iterations) {

            int [] permutation = flipBit(current);
            current = checkFitness(current, permutation, type);

            i++;
        }

        return current;

    }

    public static int [] checkFitness (int [] current, int [] permutation, String type) {

        int cost1 = 0;
        int cost2 = 0;


        switch (type) {
            case "leadingOnes":
                cost1 = leadingOnes(current);
                cost2 = leadingOnes(permutation);

                break;


            case "oneMax":

                cost1 = oneMax(current);
                cost2 = oneMax(permutation);

                break;

            default:
                System.out.println("Wrong type, please choose oneMax or leadingOnes");
                break;

        }


        int delta = cost2 - cost1;
        if (delta > 0) {
            current = permutation;
        }
        return current;
    }


    public static int [] flipBit (int [] bitString) {
        int [] copy = deepCopy(bitString);
        Random rand = new Random();
        int newRand = rand.nextInt(bitString.length);

        if (copy[newRand] == 1) {
            copy[newRand] = 0;
        } else {
            copy[newRand] = 1;
        }
        return copy;
    }

    private static int[] deepCopy(int[] bitString) {
        int [] copy = new int[bitString.length];
        System.arraycopy(bitString, 0, copy, 0, bitString.length);
        return copy;
    }

    public static int oneMax (int [] bitString) {
        int max = 0;
        for (int j : bitString) {
            if (j == 1) {
                max++;
            }
        }
        return max;
    }

    public static int leadingOnes (int [] bitString) {
        int count = 0;

        for (int j : bitString) {
            if (j == 1) {
                count++;
            }
            else {
                break;
            }
        }
        return count;
    }

    public static int[] createBitstring (int n) {
        int[] bitString = new int[n];

        Random rand = new Random();

        for (int i = 0; i < n; i++) {
            double chance = rand.nextDouble();
            if (chance < 0.5) {
                bitString[i] = 0;
            } else {
                bitString[i] = 1;
            }

        }
        return bitString;
    }




}
