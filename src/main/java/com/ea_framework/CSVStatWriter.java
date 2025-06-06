package com.ea_framework;

import java.io.*;
import java.util.*;

public class CSVStatWriter {

    public static void writeScheduleSummary(List<BatchStats> stats, File file) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(file))) {
            writer.println("Batch Index;Problem;Algorithm;Problem Size;Fitness;Best Iteration;Runtime (ms)");

            double totalFitness = 0;
            int totalIteration = 0;
            long totalRuntime = 0;
            int totalProblemSize = 0;

            for (int i = 0; i < stats.size(); i++) {
                BatchStats s = stats.get(i);
                writer.printf(Locale.US, "%d;%s;%s;%d;%.6f;%d;%d%n",
                        i,
                        s.getProblem(),
                        s.getAlgorithm(),
                        s.getProblemSize(),
                        s.getBestFitness(),
                        s.getBestIteration(),
                        s.getRuntimeMs());

                totalFitness += s.getBestFitness();
                totalIteration += s.getBestIteration();
                totalRuntime += s.getRuntimeMs();
                totalProblemSize += s.getProblemSize();
            }

            int count = stats.size();
            writer.printf(Locale.US, "Average; ; ;%d;%.6f;%d;%d%n",
                    totalProblemSize / count,
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

        double avgFitness = Double.parseDouble(values[4].trim());
        int avgIteration = (int) Double.parseDouble(values[5].trim());
        long avgRuntime = Long.parseLong(values[6].trim());

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
