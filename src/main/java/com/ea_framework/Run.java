package com.ea_framework;

import javafx.application.Application;
import javafx.stage.Stage;

public class Run extends Application {
    private static final int MAX_ITERATIONS = 50000;

    @Override
    public void start(Stage stage) throws Exception {
  /*      // Initialize the TSP components
        tspCandidate t = new tspCandidate("src/main/resources/tspFiles/st70.tsp");
        Fitness<DistanceMatrixContext<int[]>, Double> distance = new TspEuclidianDistance();
        ChoiceFunction<int[], Double> greedyMin = new GreedyChoice<int[], Double>(Comparator.reverseOrder());
        MutationOperator<int[]> twoOpt = new TwoOptTsp();
        TSPAlgorithm tspAlgo = new TSPAlgorithm(distance, twoOpt, greedyMin, t.getDistanceMatrix());

        TspCandidateView view = new TspCandidateView(t);
        FitnessView fitnessView = new GraphFitnessView(MAX_ITERATIONS);

        ConfigRecord configRecord = new ConfigRecord(
                "TSP",
                "st70",
                "Generic Algorithm",
                "Simulated Annealing",
                "EU2D",
                "Two-Opt",
                "Random"
        );

        ConfigView configView = new ConfigView();
        configView.update(configRecord);

        StatView statView = new StatView();


        tspAlgo.setCurrentSolution(t.getPermutation());

        new Thread(() -> {
            long lastUpdate = System.nanoTime();
            long updateInterval = 20_000_000; // 20ms in nanoseconds

            for (int i = 0; i < MAX_ITERATIONS; i++) {
                int n = i;
                tspAlgo.run(i);
                t.setPermutation(tspAlgo.getCurrentSolution());

                double fitness = tspAlgo.getCurrentFitness();

                StatRecord statRecord = new StatRecord(
                        i,
                        i*2,
                        tspAlgo.getCurrentFitness(),
                        tspAlgo.getBestIteration(),
                        tspAlgo.getBestIteration()*2,
                        tspAlgo.getBestFitness()
                );

                long now = System.nanoTime();
                if (now - lastUpdate >= updateInterval) {
                    lastUpdate = now;
                    Platform.runLater(() -> {
                        view.update(t);
                        fitnessView.update(fitness, n);
                        statView.update(statRecord);
                    });
                }

                try {
                    Thread.sleep(1);
                } catch (InterruptedException ignored) {}
            }
        }).start();
    }

    public static void main(String[] args) {
        launch(); // JavaFX launch
     */
    }
}