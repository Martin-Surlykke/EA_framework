package com.ea_framework.Candidates;

import com.ea_framework.Filehandlers.tspFileHandler;
import com.ea_framework.StartAlgorithms.StartAlgorithm;

import java.io.IOException;

public class tspCandidate extends Candidate{

    private  String Name;

    private  String Comment;

    private  String type;

    private  String edge_weight_type;

     int [][] edgeList;

    double [][] distanceMatrix;

     int [] permutation;

    private int [] start;

    @Override
    public Candidate clone() {
        return null;
    }

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

    public tspCandidate(String filePath, StartAlgorithm startAlgorithm) throws IOException {
        setStartCandidate(filePath);
        this.start = startAlgorithm.firstPermutation(edgeList, distanceMatrix);

    }

    public  String getName() {
        return Name;
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

    public  void setFirstPermutation(StartAlgorithm startAlgorithm) {
        start = startAlgorithm.firstPermutation(edgeList, distanceMatrix);
    }
}
