package com.ea_framework;

import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.ChoiceFunctions.GreedyChoice;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.FitnessFunctions.TspEuclidianDistance;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.Mutation.TwoOptTsp;
import com.ea_framework.View.CandidateView.TspCandidateView;
import com.ea_framework.View.FitnessView.FitnessView;
import com.ea_framework.View.FitnessView.GraphFitnessView;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.util.Comparator;

public class Run extends Application {
    private static final int MAX_ITERATIONS = 1000;

    @Override
    public void start(Stage stage) throws Exception {
        // Initialize the TSP components
        tspCandidate t = new tspCandidate("src/main/resources/tspFiles/test25.tsp");
        Fitness<DistanceMatrixContext<int[]>, Double> distance = new TspEuclidianDistance();
        ChoiceFunction<int[], Double> greedyMin = new GreedyChoice<int[], Double>(Comparator.reverseOrder());
        MutationOperator<int[]> twoOpt = new TwoOptTsp();
        TSPAlgorithm tspAlgo = new TSPAlgorithm(distance, twoOpt, greedyMin, t.getDistanceMatrix());

        TspCandidateView view = new TspCandidateView(t);
        FitnessView fitnessView = new GraphFitnessView(MAX_ITERATIONS);

        VBox layout = new VBox();

        layout.getChildren().addAll(
                 view.getView(),
                 fitnessView.getView()
        );

        Scene scene = new Scene(layout, 800, 1000);
        stage.setScene(scene);
        stage.setTitle("TSP Visualizer");
        stage.show();

        tspAlgo.setCurrentSolution(t.getPermutation());

        new Thread(() -> {
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                tspAlgo.run(i);
                t.setPermutation(tspAlgo.getCurrentSolution());

                double fitness = tspAlgo.getCurrentFitness();

                Platform.runLater(() -> {
                        view.update(t);
                        fitnessView.update(fitness, MAX_ITERATIONS);
            });
                try {
                    Thread.sleep(25);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(); // JavaFX launch
    }
}