package com.ea_framework.Problems;

import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.VisualizeView;
import javafx.scene.Node;

public interface Problem <SolutionType> {
    String getName();

    VisualizeView<?> getVisualizeView(); // or something more specific like TspVisualizeView
    FitnessView getFitnessView();
    ConfigurationView getConfigView();
    StatView getStatView();



    void setMaxIterations(int maxIterations);

    void setDefaultPermutation(SolutionType permutation);
    SolutionType getDefaultPermutation();


}
