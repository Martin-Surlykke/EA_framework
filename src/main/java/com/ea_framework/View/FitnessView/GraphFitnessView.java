package com.ea_framework.View.FitnessView;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class GraphFitnessView implements FitnessView {

    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series<Number, Number> fitnessSeries;
    private int iteration = 0;

    public GraphFitnessView(int MAX_ITERATIONS) {
        NumberAxis xAxis = new NumberAxis(0, MAX_ITERATIONS, MAX_ITERATIONS / 10.0);
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Iteration");
        yAxis.setLabel("Fitness");

        lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Fitness over Iterations");
        lineChart.setAnimated(false);
        lineChart.setPrefSize(800, 300);

        fitnessSeries = new XYChart.Series<>();
        lineChart.getData().add(fitnessSeries);
    }
    @Override
    public Node getView() {
        return lineChart;
    }

    @Override
    public void update(Number fitness, int iterations) {
        fitnessSeries.getData().add(new XYChart.Data<>(iterations, fitness));
    }

    public void reset() {
        fitnessSeries.getData().clear();
        iteration = 0;
    }
}
