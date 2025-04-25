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
    static int [][] edgeList;
    static double [][] distanceMatrix;
    static int [] permutation;

    static int [][] coordinateList;

    static int maxX;
    static int maxY;

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
        createFirstPermutation(nodeList);
        createCoordinateList(nodeList);
        setMaxVals(nodeList);
    }

    public static void setMaxVals (ArrayList<Integer []>  list) {
        for (Integer[] node : list) {
            {
                if (node[1] > maxX) {
                    maxX = node[1];
                }
                if (node[2] > maxY) {
                    maxY = node[2];
                }
            }
        }
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
        coordinateList = new int[list.size()][3];

        for (int i = 0; i < list.size(); i++) {
            coordinateList[i][0] = list.get(i)[0];
            coordinateList[i][1] = list.get(i)[1];
            coordinateList[i][2] = list.get(i)[2];
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

    public static void createFirstPermutation(ArrayList<Integer []> arr){
        permutation = new int[arr.size()];
        for (int i = 0; i < arr.size(); i++){
            permutation[i] = arr.get(i)[0];
        }
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


    public static int [][] getEdgeList(){
        return edgeList;
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


    public static int getMaxX() {
        return maxX;
    }

    public static int getMaxY() {
        return maxY;
    }
}
