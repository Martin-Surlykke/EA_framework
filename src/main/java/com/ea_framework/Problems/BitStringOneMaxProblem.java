package com.ea_framework.Problems;

import com.ea_framework.HasFitness;
import com.ea_framework.OperatorType;
import com.ea_framework.Operators.FitnessFunctions.BitStringOneMax;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.FitnessView.StandardFitnessCurve;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.BitStringVisualizeView;
import com.ea_framework.Views.VisualizeView.VisualizeView;

import java.util.List;
import java.util.Map;

public class BitStringOneMaxProblem implements Problem, BitStringCompatible, HasFitness {

    private boolean[] defaultBitString;
    private final String name;
    private final int length;
    private int maxIterations;

    public double saAlpha;
    public double saT0;

    public final Fitness fitnessFunction = new BitStringOneMax();

    public BitStringOneMaxProblem(String name, int length, boolean[] defaultBitString) {
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

    @Override
    public Fitness getFitnessFunction() {
        return fitnessFunction;
    }

}