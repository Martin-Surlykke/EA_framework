package com.ea_framework;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class tspHandler {

    static String Name;
    static String Comment;
    static String type;
    static int factor;

    static String edge_weight_type;

    static ArrayList<Integer []> nodeList = new ArrayList<>();
    static int [][] edgeList;
    static double [][] distanceMatrix;
    static int [] permutation;


    public static void handleTSP () {
        try {
            File tsp = new File("src/main/resources/tspFiles/st70.tsp");
            Scanner sc = new Scanner(tsp);

            String word = sc.nextLine();
            word = word.replace("NAME : ", "");
            Name = word;

            word = sc.nextLine();
            word = word.replace("COMMENT : ", "");
            Comment = word;

            word = sc.nextLine();
            word = word.replace("TYPE : ", "");
            type = word;

            word = sc.nextLine();
            word = word.replace("EDGE_WEIGHT_TYPE : ", "");
            edge_weight_type = word;

            sc.nextLine();
            sc.nextLine();


            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                if (line.equals("EOF")) {
                    System.out.println("Coordinate list is done");
                    break;
                }

                Scanner lineScanner = new Scanner(line);
                int index = lineScanner.nextInt();
                int coord1 = lineScanner.nextInt();
                int coord2 = lineScanner.nextInt();

                nodeList.add(new Integer[]{index, coord1, coord2});

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        createDistanceMatrix();
        createFirstPermutation(nodeList);
        createEdgeList(permutation);
    }

    public static int getLargestVal (ArrayList<Integer []>  list) {
        int largest = 0;
        for (Integer[] integers : list) {
            for (int j = 1; j <= 2; j++) {
                if (integers[j] > largest) {
                    largest = integers[j];
                }
            }
        }
        return largest;
    }

    public static void createDistanceMatrix() {
        distanceMatrix = new double[nodeList.size()][nodeList.size()];

        for (int i = 0; i < nodeList.size(); i++) {
            for (int j = 0; j < nodeList.size(); j++) {
                distanceMatrix[i][j] = euclidianDistance(nodeList.get(i)[1], nodeList.get(i)[2],
                        nodeList.get(j)[1], nodeList.get(j)[2]);
            }
        }
    }

    public static double [][] getDistanceMatrix(){
        return distanceMatrix;
    }

    public static double euclidianDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
    }

    public static void createFirstPermutation(ArrayList<Integer []> arr){
        permutation = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++){
            permutation[i] = arr.get(i)[0];
        }

        fisherYatesShuffle(permutation);
    }

    public static int [] getPermutation(){
        return permutation;
    }


    public static void createEdgeList (int [] list) {
        edgeList = new int[list.length][2];

        for (int i = 0; i < list.length; i++){
            int node1 = list[i];
            int node2;
            if (i == list.length-1){
                node2 = list[0];
            } else {
                node2 = list[i+1];
            }

            edgeList[i][0] = node1;
            edgeList[i][1] = node2;
        }

    }

    public static int [][] getEdgeList(){
        return edgeList;
    }


    public static void fisherYatesShuffle(int [] arr){

        Random rand = new Random();

        for (int i = arr.length-1; i > 0; i--){
            int j = rand.nextInt(i);
            int temp = arr[j];
            arr[j] = arr[i];
            arr[i] = temp;

        }
    }


    public static void printPermutation(int [] arr){
        System.out.print(Arrays.toString(arr));
    }

    public static void setEdgelist(int[][] anneal) {
        edgeList = anneal;
    }
}
