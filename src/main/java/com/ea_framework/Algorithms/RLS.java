package com.ea_framework.Algorithms;

import java.util.Random;

public class RLS {

    static int N;

    public static void main (String[] args) {
        N = 100;
        int[] bitString = new int[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            int randInt = rand.nextInt(2);
            bitString[i] = randInt;
        }

        int current = oneMax(bitString);
        System.out.println(current);
        flipBit(bitString);
        int newVal = oneMax(bitString);
        System.out.println(newVal);
    }


    public static void flipBit (int [] bitString) {
        Random rand = new Random();
        int newRand = rand.nextInt(N);

        if (bitString[newRand] == 1) {
            bitString[newRand] = 0;
        } else {
            bitString[newRand] = 1;
        }
    }

    public static int oneMax (int [] bitString) {
        int max = 0;
        for (int i = 0; i < bitString.length; i++) {
            if (bitString[i] == 1) {
                max++;
            }
        }
        return max;
    }


    public static void printString(int [] bitString) {
        for (int i = 0; i < N; i++) {
            System.out.print(bitString[i] + " ");
        }
    }




}
