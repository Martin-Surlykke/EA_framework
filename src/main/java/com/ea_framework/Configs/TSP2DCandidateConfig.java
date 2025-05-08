package com.ea_framework.Configs;

import com.ea_framework.Coordinate;

import java.util.List;

public record TSP2DCandidateConfig(String name, String comment, String type, String edgeWeightType, List<Coordinate> coordinates,
                                   double[][] distanceMatrix, int[] permutation, double maxX, double maxY, double minX,
                                   double minY) {


}