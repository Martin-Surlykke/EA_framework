package com.ea_framework.Runners;

import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Controllers.VisualizeController;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Views.InfoViews.StatRecord;
import javafx.animation.AnimationTimer;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class Runner {

    public void run(Problem problem,
                    Algorithm algorithm,
                    VisualizeController controller,
                    int termination) {

        Object initial = problem.getDefaultPermutation();
        algorithm.setCurrentSolution(initial);

        AtomicInteger latestIteration = new AtomicInteger();
        AtomicReference<Double> latestFitness = new AtomicReference<>();
        AtomicReference<StatRecord> latestStat = new AtomicReference<>();

        Thread solver = new Thread(() -> {
            try {
                Thread.sleep(500);
            } catch (InterruptedException ignored) {}

            for (int i = 0; i < termination; i++) {
                algorithm.run(i);

                latestIteration.set(i);
                latestFitness.set(algorithm.getCurrentFitness());
                latestStat.set(new StatRecord(
                        i,
                        i * 2,
                        algorithm.getCurrentFitness(),
                        algorithm.getBestIteration(),
                        algorithm.getBestIteration() * 2,
                        algorithm.getBestFitness()
                ));
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        solver.setDaemon(true);
        solver.start();

        new AnimationTimer() {
            private long lastUpdate = 0;
            private final long interval = 20_000_000;

            @Override
            public void handle(long now) {
                if (now - lastUpdate < interval) return;
                lastUpdate = now;

                if (latestFitness.get() == null || latestStat.get() == null) return;

                int i = latestIteration.get();
                double fitness = latestFitness.get();
                StatRecord stat = latestStat.get();

                controller.updateAll(algorithm.getCurrentSolution(), i, fitness, stat);

                if (i >= termination) stop();
            }
        }.start();
    }
}
