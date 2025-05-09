package com.ea_framework.Views.ConfigViews;

import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.ChoiceFunctions.GreedyChoice;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.TSP2DConfig;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.FitnessFunctions.TspEuclidianDistance;
import com.ea_framework.MutationFunctions.MutationOperator;
import com.ea_framework.MutationFunctions.TwoOptTsp;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.util.Comparator;

public class TSP2DConfigView implements ConfigView {
    @FXML
    Label fitnessLabel;

    @FXML
    Label mutationLabel;

    @FXML
    Label choiceLabel;

    private Fitness<int[], Double> fitness;
    private MutationOperator<int[]> mutation;
    private ChoiceFunction<int[], Double> choice;

    private double[][] distanceMatrix;



    public void setDistanceMatrix(double[][] distanceMatrix) {
        this.distanceMatrix = distanceMatrix;
    }

    @Override
    public Node getUI() {
        VBox vbox = new VBox();
        vbox.getChildren().addAll(
                new Label("tester")
        );
        return vbox;
    }

    @Override
    public AlgorithmConfig getConfig() {
        if (fitness == null) {
            fitness = new TspEuclidianDistance(distanceMatrix); // uses the matrix
        }

        if (mutation == null) {
            mutation = new TwoOptTsp(); // or whatever default mutation operator
        }

        if (choice == null) {
            choice = new GreedyChoice<int[], Double>(Comparator.reverseOrder());
        }

        return new TSP2DConfig(fitness, mutation, choice);
    }
}
