package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;
import com.ea_framework.Termination.TerminationCondition;
import com.ea_framework.Views.InfoViews.StatRecord;
import com.ea_framework.Views.ProgressDialog;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunBatch {

    private final BatchConfig config;
    private final Stage stage;
    private final Scene returnScene;
    private final int batchIndex;
    private final List<BatchStats> statsCollector;

    public RunBatch(BatchConfig config, Stage stage, Scene returnScene, int batchIndex, List<BatchStats> statsCollector) {
        this.config = config;
        this.stage = stage;
        this.returnScene = returnScene;
        this.batchIndex = batchIndex;
        this.statsCollector = statsCollector;
    }

    public void runAsync(Runnable onComplete) {
        new Thread(() -> {
            Problem problem;
            try {
                problem = config.resolveProblem();
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            AlgorithmConfig algoConfig = config.getAlgorithmConfig();
            Algorithm algorithm = config.getAlgorithmDescriptor().create(algoConfig);
            List<TerminationCondition> terminationConditions = config.getTerminationConditions();

            if (config.getVisualSelected()) {
                runWithVisualization(problem, algorithm, terminationConditions, onComplete);
            } else {
                runHeadless(problem, algorithm, terminationConditions, onComplete);
            }
        }).start();
    }

    private void runWithVisualization(Problem problem, Algorithm algorithm, List<TerminationCondition> terminationConditions, Runnable onComplete) {
        List<StatRecord> stats = Collections.synchronizedList(new ArrayList<>());

        Platform.runLater(() -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/Visualizer.fxml"));
                Scene scene = new Scene(loader.load());
                stage.setScene(scene);
                stage.setTitle("Visualizer");

                VisualizeController controller = loader.getController();
                controller.initialize(
                        problem.getVisualizer(),
                        problem.getFitnessView(),
                        problem.getConfigView(),
                        problem.getStatView(),
                        stage
                );

                StatTracker statTracker = (i, solution, fitness, stat) -> {
                    stats.add(stat);
                    Platform.runLater(() -> controller.updateAll(solution, i, fitness, stat));
                };

                stage.show();

                new Thread(() -> {
                    long startTime = System.nanoTime();

                    Runner runner = new Runner();
                    controller.setRunner(runner);
                    runner.run(problem, algorithm, terminationConditions, statTracker, controller::getSleepDelay, () -> {

                    long endTime = System.nanoTime();
                        long runtimeMs = (endTime - startTime) / 1_000_000;

                        Platform.runLater(() -> {
                            statsCollector.add(BatchStats.from(config, problem, algorithm, runtimeMs));

                            controller.enableNextRun();
                            controller.setOnNextRun(() -> {
                                stage.setScene(returnScene);
                                stage.setTitle("EA Framework");
                                if (onComplete != null) onComplete.run();
                            });
                        });
                    });
                }).start();

            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void runHeadless(Problem problem, Algorithm algorithm, List<TerminationCondition> terminationConditions, Runnable onComplete) {
        final ProgressDialog[] progressDialog = new ProgressDialog[1];

        Platform.runLater(() -> {
            progressDialog[0] = new ProgressDialog("Running batch " + batchIndex + "...");
            progressDialog[0].show(stage);
        });

        new Thread(() -> {
            Object initial = problem.getDefaultPermutation();
            algorithm.setCurrentSolution(initial);

            long startTime = System.nanoTime();
            int iteration = 0;

            while (!terminationMet(terminationConditions, algorithm, iteration)) {
                algorithm.run(iteration);
                iteration++;
            }

            long endTime = System.nanoTime();
            long runtimeMs = (endTime - startTime) / 1_000_000;

            BatchStats batchStats = BatchStats.from(config, problem, algorithm, runtimeMs);
            statsCollector.add(batchStats);

            Platform.runLater(() -> {
                progressDialog[0].close();
                if (onComplete != null) onComplete.run();
            });
        }).start();
    }

    private boolean terminationMet(List<TerminationCondition> conditions, Algorithm algorithm, int iteration) {
        for (TerminationCondition cond : conditions) {
            if (cond.shouldTerminate(iteration, algorithm)) {
                return true;
            }
        }
        return false;
    }
}
