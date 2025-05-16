package com.ea_framework.Problems;

import com.ea_framework.OperatorType;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.FitnessView.StandardFitnessCurve;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.InfoViews.boxes.StandardConfigBox;
import com.ea_framework.Views.VisualizeView.BitStringVisualizeView;
import com.ea_framework.Views.VisualizeView.VisualizeView;

import java.util.List;
import java.util.Map;

public class BitStringProblem implements Problem {

    private boolean[] defaultBitString;
    private final String name;
    private final int length;
    private int maxIterations;

    public double saAlpha;
    public double saT0;

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
    public VisualizeView getVisualizer() {
        return new BitStringVisualizeView(this);
    }

    @Override
    public FitnessView getFitnessView() {
        return new StandardFitnessCurve(maxIterations);
    }

    @Override
    public ConfigurationView getConfigView() {
        return new ConfigurationView();
    }

    @Override
    public StatView getStatView() {
        return new StatView();
    }

    @Override
    public void setMaxIterations(int maxIterations) {
        this.maxIterations = maxIterations;
    }

    @Override
    public void setDefaultPermutation(Object permutation) {
        this.defaultBitString = (boolean[]) permutation;
    }

    @Override
    public boolean[] getDefaultPermutation() {
        return defaultBitString;
    }

    @Override
    public Map<OperatorType, Object> getOperatorConfigurations() {
        return Map.of();
    }

    public int getLength() {
        return length;
    }

    public double getAlpha() { return saAlpha; }
    public double getT0() { return saT0; }

    @Override
    public void setSimulatedAnnealingParams(double alpha, double t0) {
        this.saAlpha = alpha;
        this.saT0 = t0;
    }

    @Override
    public List<Double> getSimulatedAnnealingParams() {
        return List.of(saAlpha, saT0);
    }

}