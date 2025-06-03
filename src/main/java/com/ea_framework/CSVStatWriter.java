package com.ea_framework;

import java.io.*;
import java.util.*;

public class CSVStatWriter {

    public static void writeScheduleSummary(List<BatchStats> stats, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Batch Index;Problem;Algorithm;Fitness;Best Iteration;Runtime (ms)");

            double totalFitness = 0;
            int totalIteration = 0;
            long totalRuntime = 0;

            for (int i = 0; i < stats.size(); i++) {
                BatchStats s = stats.get(i);
                writer.printf(Locale.US, "%d;%s;%s;%.6f;%d;%d%n",
                        i,
                        s.getProblem(),
                        s.getAlgorithm(),
                        s.getBestFitness(),
                        s.getBestIteration(),
                        s.getRuntimeMs());

                totalFitness += s.getBestFitness();
                totalIteration += s.getBestIteration();
                totalRuntime += s.getRuntimeMs();
            }

            int count = stats.size();
            writer.printf(Locale.US, "Average; ; ;%.6f;%d;%d%n",
                    totalFitness / count,
                    totalIteration / count,
                    totalRuntime / count);
        }
    }

    public static void appendToFullScheduleSummary(File fullFile, File scheduleSummaryFile) throws IOException {
        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(scheduleSummaryFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }

        String averageLine = lines.get(lines.size() - 1);
        String[] values = averageLine.split(";");

        double avgFitness = Double.parseDouble(values[3].trim());
        int avgIteration = Integer.parseInt(values[4].trim());
        long avgRuntime = Long.parseLong(values[5].trim());

        boolean newFile = !fullFile.exists();
        try (PrintWriter writer = new PrintWriter(new FileWriter(fullFile, true))) {
            if (newFile) {
                writer.println("Schedule Summary File;Average Fitness;Average Best Iteration;Average Runtime (ms)");
            }

            writer.printf(Locale.US, "%s;%.6f;%d;%d%n",
                    scheduleSummaryFile.getName(),
                    avgFitness,
                    avgIteration,
                    avgRuntime);
        }
    }
}
