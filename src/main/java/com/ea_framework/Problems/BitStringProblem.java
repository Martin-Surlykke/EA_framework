package com.ea_framework.Problems;

import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.VisualizeView;

public class BitStringProblem implements Problem<boolean[]> {

    private boolean[] defaultBitString;
    private final String name;
    private final int length;
    private int maxIterations;

    public BitStringProblem(String name, int length, boolean[] defaultBitString) {
        this.name = name;
        this.length = length;
        this.defaultBitString = defaultBitString;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public VisualizeView<?> getVisualizeView() {
        return null; // Implement when ready
    }

    @Override
    public FitnessView getFitnessView() {
        return null;
    }

    @Override
    public ConfigurationView getConfigView() {
        return null;
    }

    @Override
    public StatView getStatView() {
        return null;
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public void setDefaultPermutation(boolean[] permutation) {
        this.defaultBitString = permutation;
    }

    @Override
    public boolean[] getDefaultPermutation() {
        return defaultBitString;
    }

    public int getLength() {
        return length;
    }
}