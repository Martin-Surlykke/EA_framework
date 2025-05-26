package com.ea_framework.Runners;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Problems.Problem;
import com.ea_framework.StatTracker;
import com.ea_framework.Views.InfoViews.StatRecord;
import javafx.application.Platform;

public class Runner {
    private volatile int latestIteration = -1;
    private volatile Object latestSolution = null;
    private volatile double latestFitness = Double.NaN;
    private volatile StatRecord latestStat = null;

    public void run(Problem problem, Algorithm algorithm, int termination, StatTracker stats, Runnable onComplete) {
        Object initial = problem.getDefaultPermutation();
        algorithm.setCurrentSolution(initial);

        Thread solver = new Thread(() -> {
            for (int i = 0; i < termination; i++) {
                algorithm.run(i);

                latestIteration = i;
                latestSolution = algorithm.getCurrentSolution();
                latestFitness = algorithm.getCurrentFitness();
                latestStat = new StatRecord(i, i * 2, latestFitness,
                        algorithm.getBestIteration(), algorithm.getBestIteration() * 2,
                        algorithm.getBestFitness());

                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            if (onComplete != null) {
                Platform.runLater(onComplete);
            }
        });

        solver.setDaemon(false);
        solver.start();


        startUpdateTimer(stats, termination);
    }

    private void startUpdateTimer(StatTracker stats, int termination) {
        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long intervalNs = 15_000_000;
                if (now - lastUpdate < intervalNs) return;
                lastUpdate = now;

                if (stats != null && latestIteration >= 0 && latestIteration < termination) {
                    stats.onIteration(latestIteration, latestSolution, latestFitness, latestStat);
                }

                if (latestIteration >= termination - 1) {
                    stop();
                }
            }
        };
        timer.start();
    }
}
