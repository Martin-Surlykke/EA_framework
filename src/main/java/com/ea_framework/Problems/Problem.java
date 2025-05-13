package com.ea_framework.Problems;

import com.ea_framework.OperatorType;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.VisualizeView;

import java.util.List;
import java.util.Map;

public interface Problem {

    String getName();

    VisualizeView getVisualizer();
    FitnessView getFitnessView();
    ConfigurationView getConfigView();
    StatView getStatView();

    void setMaxIterations(int maxIterations);

    void setDefaultPermutation(Object permutation);
    Object getDefaultPermutation();

    Map<OperatorType, Object> getOperatorConfigurations();

    void setSimulatedAnnealingParams(double alpha, double t0);

    List<Double> getSimulatedAnnealingParams();
}
