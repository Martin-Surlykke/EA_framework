package com.ea_framework.Model;

import com.ea_framework.Coordinate;

import java.util.List;

public class tspInstance {
    public final String name;
    public final String comment;
    public final String type;
    public final String edgeWeightType;

    public final List<Coordinate> coordinates;
    public final double[][] distanceMatrix;

    public final int[] permutation;
    public final double maxX;
    public final double maxY;
    public final double minX;
    public final double minY;

    public tspInstance(String name, String comment, String type, String edgeWeightType,
                       List<Coordinate> coordinates, double[][] distanceMatrix, int[] permutation, double maxX, double maxY, double minX, double minY) {
        this.name = name;
        this.comment = comment;
        this.type = type;
        this.edgeWeightType = edgeWeightType;
        this.coordinates = coordinates;
        this.distanceMatrix = distanceMatrix;
        this.permutation = permutation;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
    }

    public int[] getPermutation() {
        return permutation;
    }


}