package com.ea_framework.Candidates;

import com.ea_framework.Coordinate;
import com.ea_framework.Model.tspInstance;

import java.io.IOException;
import java.util.List;

public class tspCandidate implements Candidate{

    private final tspInstance instance;

    private int[] permutation;

    private int[] startPermutation;

    public tspCandidate(tspInstance instance) {
        this.instance = instance;
        this.permutation = instance.getPermutation();

    }


    @Override
    public void setStartCandidate(String filePath) throws IOException {

    }

    @Override
    public String stringify() {
        StringBuilder sb = new StringBuilder();
        for (int i : permutation) {
            sb.append(i).append(" ");
        }
        return sb.toString();
    }



    public String getName() {
        return instance.name;
    }

    public String getComment() {
        return instance.comment;
    }

    public String getType() {
        return instance.type;
    }

    public String getEdgeWeightType() {
        return instance.edgeWeightType;
    }

    public int getNodeCount() {
        return instance.coordinates.size();
    }

    public double[][] getDistanceMatrix() {
        return instance.distanceMatrix;
    }

    public List<Coordinate> getCoordinateList() {
        return instance.coordinates;
    }

    public double getMaxX() {
        return instance.maxX;
    }

    public double getMaxY() {
        return instance.maxY;
    }

    public int[] getPermutation() {
        return permutation;
    }

    public int[] getStartPermutation() {
        return startPermutation;
    }

    public void setStartPermutation(int[] startPermutation) {
        this.startPermutation = startPermutation;
    }

    public void setPermutation(int[] permutation) {
        this.permutation = permutation;
    }

    public double getMinX() {
        return instance.minX;
    }

    public double getMinY() {
        return instance.minY;
    }
}
