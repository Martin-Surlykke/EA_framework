package com.ea_framework;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Views.InfoViews.StatRecord;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class CSVStatWriter {

    public static void writeFullStats(List<StatRecord> stats, File file, double time) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Iteration,Time,Fitness,Best Iteration,Best Time,Best Fitness");

            for (StatRecord stat : stats) {
                writer.printf("%d,%.6f,%d,%.6f%n",
                        stat.iteration(),
                        stat.fitness(),
                        stat.bestIteration(),
                        stat.bestFitness());
            }
        }
    }

    public static void writeBatchSummary(BatchConfig config, Algorithm algorithm, File file, long runtimeMs) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Problem,Algorithm,Fitness,Best Iteration,Runtime (ms),Solution");
            writer.printf("%s,%s,%.6f,%d,%d,%s%n",
                    config.getProblemName(),
                    config.getAlgorithmName(),
                    algorithm.getBestFitness(),
                    algorithm.getBestIteration(),
                    runtimeMs,
                    algorithm.getCurrentSolution().toString().replaceAll(",", "-"));
        }
    }

    public static void appendToScheduleSummary(File scheduleSummaryFile, int batchIndex, BatchConfig config, Algorithm algorithm, long runtimeMs) throws IOException {
        boolean newFile = !scheduleSummaryFile.exists();

        try (PrintWriter writer = new PrintWriter(new FileWriter(scheduleSummaryFile, true))) {
            if (newFile) {
                writer.println("Batch Index,Problem,Algorithm,Fitness,Best Iteration,Runtime (ms)");
            }

            writer.printf("%d,%s,%s,%.6f,%d,%d%n",
                    batchIndex,
                    config.getProblemName(),
                    config.getAlgorithmName(),
                    algorithm.getBestFitness(),
                    algorithm.getBestIteration(),
                    runtimeMs);
        }
    }
}