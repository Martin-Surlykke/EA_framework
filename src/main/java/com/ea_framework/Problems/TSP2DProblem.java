package com.ea_framework.Problems;

import com.ea_framework.Coordinate;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.FitnessView.GraphFitnessView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.TspVisualizeView;
import com.ea_framework.Views.VisualizeView.VisualizeView;

import java.util.List;

public class TSP2DProblem implements Problem<int []> {
    private final String name;
    private final String comment;
    private final String type;
    private final String edgeWeightType;
    private final List<Coordinate> coordinates;
    private final double[][] distanceMatrix;
    private int[] defaultPermutation;
    private final double maxX;
    private final double maxY;
    private final double minX;
    private final double minY;

    private int maxIterations;

    private final int nodeCount;

    public TSP2DProblem(String name,
                        String comment,
                        String type,
                        String edgeWeightType,
                        List<Coordinate> coordinates,
                        double[][] distanceMatrix,
                        int[] defaultPermutation,
                        double maxX,
                        double maxY,
                        double minX,
                        double minY) {
        this.name = name;
        this.comment = comment;
        this.type = type;
        this.edgeWeightType = edgeWeightType;
        this.coordinates = coordinates;
        this.distanceMatrix = distanceMatrix;
        this.defaultPermutation = defaultPermutation;
        this.maxX = maxX;
        this.maxY = maxY;
        this.minX = minX;
        this.minY = minY;
        this.nodeCount = coordinates.size();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public VisualizeView<int []> getVisualizeView() {
        return new TspVisualizeView(this);
    }

    @Override
    public FitnessView getFitnessView() {
        return new GraphFitnessView(maxIterations);
    }

    @Override
    public ConfigurationView getConfigView() {
        return new ConfigurationView();

    }



    @Override
    public StatView getStatView() {
        return new StatView();
    }

    public String getComment() {
        return comment;
    }
    public String getType() {
        return type;
    }
    public String getEdgeWeightType() {
        return edgeWeightType;
    }
    public List<Coordinate> getCoordinates() {
        return coordinates;
    }
    public double[][] getDistanceMatrix() {
        return distanceMatrix;
    }
    public int[] getDefaultPermutation() {
        if (defaultPermutation == null || defaultPermutation.length == 0) {
            throw new IllegalStateException("Default permutation was not set.");
        }
        return defaultPermutation;
    }

    public void setDefaultPermutation(int[] permutation) {
        this.defaultPermutation = permutation;
    }

    public double getMaxX() {
        return maxX;
    }
    public double getMaxY() {
        return maxY;
    }
    public double getMinX() {
        return minX;
    }
    public double getMinY() {
        return minY;
    }

    public int getNodeCount() {
        return nodeCount;
    }
}