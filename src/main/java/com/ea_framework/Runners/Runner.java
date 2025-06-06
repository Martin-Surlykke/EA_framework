package com.ea_framework.Runners;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Problems.Problem;
import com.ea_framework.StatTracker;
import com.ea_framework.Termination.TerminationCondition;
import com.ea_framework.Views.InfoViews.StatRecord;
import javafx.application.Platform;

import java.util.ArrayList;
import java.util.List;

public class Runner {
    private volatile int latestIteration = -1;
    private volatile Object latestSolution = null;
    private volatile double latestFitness = Double.NaN;
    private volatile StatRecord latestStat = null;

    private final List<TerminationCondition> terminationConditions = new ArrayList<>();

    public void addTerminationCondition(TerminationCondition condition) {
        terminationConditions.add(condition);
    }

    private volatile boolean paused = false;

    public void setPaused(boolean paused) {
        this.paused = paused;
    }

    @FunctionalInterface
    public interface DelayProvider {
        int getDelay();
    }


    public void run(Problem problem, Algorithm algorithm, List<TerminationCondition> conditions, StatTracker stats, DelayProvider delayProvider,  Runnable onComplete) {
        Object initial = problem.getDefaultPermutation();
        algorithm.setCurrentSolution(initial);

        Thread solver = new Thread(() -> {
            int i = 0;
            while (!terminationMet(conditions, algorithm, i)) {
                algorithm.run(i);

                latestIteration = i;
                latestSolution = algorithm.getCurrentSolution();
                latestFitness = algorithm.getCurrentFitness();
                latestStat = new StatRecord(i, i * 2, latestFitness,
                        algorithm.getBestIteration(), algorithm.getBestIteration() * 2,
                        algorithm.getBestFitness());

                while(paused) {
                    try {
                        Thread.sleep(50);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                try {
                    Thread.sleep(delayProvider.getDelay());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                i++;
            }

            if (onComplete != null) {
                Platform.runLater(onComplete);
            }
        });

        solver.setDaemon(false);
        solver.start();
        startUpdateTimer(stats);
    }

    private boolean terminationMet(List<TerminationCondition> conditions, Algorithm algorithm, int iteration) {
        for (TerminationCondition cond : conditions) {
            if (cond.shouldTerminate(iteration, algorithm)) {
                return true;
            }
        }
        return false;
    }

    private boolean isTerminated(int iteration, Algorithm algorithm) {
        for (TerminationCondition condition : terminationConditions) {
            if (condition.shouldTerminate(iteration, algorithm)) {
                return true;
            }
        }
        return false;
    }

    private void startUpdateTimer(StatTracker stats) {
        javafx.animation.AnimationTimer timer = new javafx.animation.AnimationTimer() {
            private long lastUpdate = 0;

            @Override
            public void handle(long now) {
                long intervalNs = 15_000_000;
                if (now - lastUpdate < intervalNs) return;
                lastUpdate = now;

                if (stats != null && latestIteration >= 0) {
                    stats.onIteration(latestIteration, latestSolution, latestFitness, latestStat);
                }
            }
        };
        timer.start();
    }
}
