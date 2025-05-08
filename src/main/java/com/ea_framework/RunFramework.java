package com.ea_framework;

import com.ea_framework.Algorithms.TSPAlgorithm;
import com.ea_framework.ChoiceFunctions.ChoiceFunction;
import com.ea_framework.ChoiceFunctions.GreedyChoice;
import com.ea_framework.Controller.VisualizeController;
import com.ea_framework.Filehandlers.TSPFileHandler;
import com.ea_framework.FitnessFunctions.DistanceMatrixContext;
import com.ea_framework.FitnessFunctions.Fitness;
import com.ea_framework.FitnessFunctions.TspEuclidianDistance;
import com.ea_framework.Mutation.MutationOperator;
import com.ea_framework.Mutation.TwoOptTsp;
import com.ea_framework.Problems.TSP2DProblem;
import com.ea_framework.StartAlgorithms.TSP2DRandomStart;
import com.ea_framework.View.VisualizeView.TspVisualizeView;
import com.ea_framework.View.FitnessView.FitnessView;
import com.ea_framework.View.FitnessView.GraphFitnessView;
import com.ea_framework.View.InfoViews.ConfigRecord;
import com.ea_framework.View.InfoViews.ConfigView;
import com.ea_framework.View.InfoViews.StatRecord;
import com.ea_framework.View.InfoViews.StatView;
import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import java.util.Comparator;

public class RunFramework extends Application {

    private final AtomicInteger latestIteration = new AtomicInteger(0);
    private final AtomicReference<Double> latestFitness = new AtomicReference<>(0.0);
    private final AtomicReference<StatRecord> latestStatRecord = new AtomicReference<>(new StatRecord(0, 0, 0.0, 0, 0, 0.0));

    @Override
    public void start(Stage stage) throws Exception {

        int MAX_ITERATIONS = 25000;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/ea_framework/Visualizer.fxml"));

        Scene scene = new Scene(fxmlLoader.load());

        TSP2DProblem tsp = TSPFileHandler.parse("src/main/resources/tspFiles/ch130.tsp");


        TSP2DRandomStart randomStart = new TSP2DRandomStart();
        tsp.setDefaultPermutation(randomStart.generateFirstSolution(tsp));

        Fitness<DistanceMatrixContext<int[]>, Double> distance = new TspEuclidianDistance();
        ChoiceFunction<int[], Double> greedyMin = new GreedyChoice<int[], Double>(Comparator.reverseOrder());
        MutationOperator<int[]> twoOpt = new TwoOptTsp();
        TSPAlgorithm tspAlgo = new TSPAlgorithm(distance, twoOpt, greedyMin, tsp.getDistanceMatrix());

        TspVisualizeView view = new TspVisualizeView(tsp);
        FitnessView fitnessView = new GraphFitnessView(MAX_ITERATIONS);

        ConfigRecord configRecord = new ConfigRecord("TSP", "st70", "Generic Algorithm", "Simulated Annealing", "EU2D", "Two-Opt", "Random");

        ConfigView configView = new ConfigView();

        configView.update(configRecord);

        StatView statView = new StatView();

        VisualizeController visualizeController = fxmlLoader.getController();

        visualizeController.initialize(view, fitnessView, configView, statView, stage);


        tspAlgo.setCurrentSolution(tsp.getDefaultPermutation());

        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.setTitle("Visualizer");
        stage.show();

        Thread solverThread = new Thread(() -> {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
            for (int i = 0; i < MAX_ITERATIONS; i++) {
                tspAlgo.run(i);

                latestFitness.set(tspAlgo.getCurrentFitness());
                latestIteration.set(i);
                latestStatRecord.set(new StatRecord(i, i * 2, tspAlgo.getCurrentFitness(), tspAlgo.getBestIteration(), tspAlgo.getBestIteration() * 2, tspAlgo.getBestFitness()));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        });
        solverThread.setDaemon(true);
        solverThread.start();


        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            private final long interval = 20_000_000; // 20ms

            @Override
            public void handle(long now) {
                if (now - lastUpdate < interval) return;
                lastUpdate = now;

                int i = latestIteration.get();
                double fitness = latestFitness.get();
                StatRecord stat = latestStatRecord.get();

                view.update(tspAlgo);
                fitnessView.update(fitness, i);
                statView.update(stat);
                if (latestIteration.get() >= MAX_ITERATIONS) {
                    this.stop();
                }
            }

        };
        timer.start();
    }


    public static void main(String[] args) {
        launch(args);
    }
}