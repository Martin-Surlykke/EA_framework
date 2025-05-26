package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Runners.Runner;
import com.ea_framework.Views.InfoViews.StatRecord;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RunBatch {

    private final BatchConfig config;
    private final Stage stage;
    private final Scene returnScene;
    private final int batchIndex;
    private final File outputDir;
    private final File scheduleSummaryFile;

    public RunBatch(BatchConfig config, Stage stage, Scene returnScene, int batchIndex, File outputDir, File scheduleSummaryFile) {
        this.config = config;
        this.stage = stage;
        this.returnScene = returnScene;
        this.batchIndex = batchIndex;
        this.outputDir = outputDir;
        this.scheduleSummaryFile = scheduleSummaryFile;
    }

    public void runAsync(Runnable onComplete) throws Exception {
        Problem problem = config.resolveProblem();
        problem.setMaxIterations(config.getTermination());

        AlgorithmConfig algoConfig = config.getAlgorithmConfig();
        Algorithm algorithm = config.getAlgorithmDescriptor().create(algoConfig);

        if (config.getVisualSelected()) {
            List<StatRecord> stats = Collections.synchronizedList(new ArrayList<>());

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

            long startTime = System.nanoTime();

            new Runner().run(problem, algorithm, config.getTermination(), statTracker, () -> {
                long endTime = System.nanoTime();
                long runtimeMs = (endTime - startTime) / 1_000_000;

                Platform.runLater(() -> {
                    try {
                        CSVStatWriter.writeFullStats(stats, new File(outputDir, "batch_" + batchIndex + "_full.csv"), runtimeMs);
                        CSVStatWriter.writeBatchSummary(config, algorithm, new File(outputDir, "batch_" + batchIndex + "_summary.csv"), runtimeMs);
                        CSVStatWriter.appendToScheduleSummary(scheduleSummaryFile, batchIndex, config, algorithm, runtimeMs);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    stage.setScene(returnScene);
                    stage.setTitle("EA Framework");
                    if (onComplete != null) onComplete.run();
                });
            });

        } else {
            new Thread(() -> {
                long startTime = System.nanoTime();
                algorithm.setCurrentSolution(problem.getDefaultPermutation());
                for (int i = 0; i < config.getTermination(); i++) {
                    algorithm.run(i);
                }
                long endTime = System.nanoTime();
                long runtimeMs = (endTime - startTime) / 1_000_000;

                try {
                    CSVStatWriter.writeBatchSummary(config, algorithm, new File(outputDir, "batch_" + batchIndex + "_summary.csv"), runtimeMs);
                    CSVStatWriter.appendToScheduleSummary(scheduleSummaryFile, batchIndex, config, algorithm, runtimeMs);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                Platform.runLater(onComplete);
            }).start();
        }
    }
}
