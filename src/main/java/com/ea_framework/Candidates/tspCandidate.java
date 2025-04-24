package com.ea_framework.Candidates;

import com.ea_framework.Filehandlers.tspFileHandler;
import com.ea_framework.StartAlgorithms.StartAlgorithm;
import com.ea_framework.StartAlgorithms.TspFromStartToEnd;

import java.io.IOException;

public class tspCandidate implements Candidate{
    private  String Name;

    private  String Comment;

    private  String type;

    private  String edge_weight_type;

     int [] edgeList;

    double [][] distanceMatrix;

     int [] permutation;

    @Override
    public void setStartCandidate(String filePath) throws IOException {
        tspFileHandler.handleTSP(filePath);
        Name = tspFileHandler.getName();
        Comment = tspFileHandler.getComment();
        type = tspFileHandler.getType();
        edge_weight_type = tspFileHandler.getEdgeWeightType();
        edgeList = tspFileHandler.getEdgeList();
        distanceMatrix = tspFileHandler.getDistanceMatrix();
        permutation = tspFileHandler.getPermutation();


        System.out.println("Name: " + Name);
        System.out.println("Comment: " + Comment);
        System.out.println("Type: " + type);
        System.out.println("EdgeWeightType: " + edge_weight_type);
    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();
        for (int i : permutation) {
            sb.append(i).append(" ");
        }
        return sb.toString();
    }

    public tspCandidate(String filePath) throws IOException {
        setStartCandidate(filePath);

    }

    public  String getName() {
        return Name;
    }

    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }

    public  String getComment() {
        return Comment;
    }

    public  String getType() {
        return type;
    }

    public  String getEdgeWeightType() {
        return edge_weight_type;
    }

    public  int[] getPermutation() {
        return permutation;
    }

    public void setPermutation(int [] permutation) {
        this.permutation = permutation;
    }
}
