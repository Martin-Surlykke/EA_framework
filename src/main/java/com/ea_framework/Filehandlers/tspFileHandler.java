package com.ea_framework.Filehandlers;

import java.io.*;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;
import java.util.ArrayList;

public class tspFileHandler {

    static String Name;
    static String Comment;
    static String type;

    static String edge_weight_type;

    static ArrayList<Integer []> nodeList = new ArrayList<>();
    static int [] edgeList;
    static double [][] distanceMatrix;
    static int [] permutation;

    static int [][] coordinateList;


    public static void handleTSP (String filePath) throws IOException {
        try {
            File tsp = new File(filePath);
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
                int index = (lineScanner.nextInt())-1;
                int coord1 = lineScanner.nextInt();
                int coord2 = lineScanner.nextInt();

                nodeList.add(new Integer[]{index, coord1, coord2});

            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found");
        }

        createDistanceMatrix();
        permutation = createFirstPermutation(nodeList);
    }

    public static int [] getLargestVal (ArrayList<Integer []>  list) {
        int [] max = new int [2];

        for (Integer[] integers : list) {
            for (int j = 1; j <= 2; j++) {
                if (integers[j] > max[j-1]) {
                    max[j-1] = integers[j];
                }
            }
        }
        return max;
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

    public static void createCoordinateList(ArrayList<Integer []>  list) {
        coordinateList = new int[3][list.size()];

        for (int i = 0; i < list.size(); i++) {
            for (int j = 0; j < 3; j++) {
                coordinateList[i][j] = list.get(i)[j];
            }
        }


    }

    public static int [][] getCoordinateList () {
        return coordinateList;
    }

    public static double [][] getDistanceMatrix(){
        return distanceMatrix;
    }

    public static double euclidianDistance(int x1, int y1, int x2, int y2) {
        return Math.sqrt(Math.pow((x2-x1),2)+Math.pow((y2-y1),2));
    }

    public static int[] createFirstPermutation(ArrayList<Integer []> arr){
        permutation = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++){
            permutation[i] = arr.get(i)[0];
        }
        return permutation;
    }

    public static int [] getNodeIndex () {
        int [] nodeIndex = new int[nodeList.size()];
        for (int i = 0; i < nodeList.size(); i++) {
            nodeIndex[i] = nodeList.get(i)[0]-1;
        }
        return nodeIndex;
    }

    public static int [] getPermutation(){
        return permutation;
    }


    public static int [] getEdgeList(){
        return edgeList;
    }


    public static void printPermutation(int [] arr){
        System.out.print(Arrays.toString(arr));
    }

    public static void setEdgelist(int[] input) {
        edgeList = input.clone();
    }

    public static String getName() {
        return Name;
    }

    public static String getComment() {
        return Comment;
    }
    public static String getType() {
        return type;
    }
    public static String getEdgeWeightType() {
        return edge_weight_type;
    }


}
