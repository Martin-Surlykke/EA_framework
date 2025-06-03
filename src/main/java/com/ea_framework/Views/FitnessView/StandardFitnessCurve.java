package com.ea_framework.Views.FitnessView;

import javafx.scene.Node;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;

public class StandardFitnessCurve implements FitnessView {

    private final LineChart<Number, Number> lineChart;
    private final XYChart.Series<Number, Number> fitnessSeries;

    public StandardFitnessCurve() {
        NumberAxis xAxis = new NumberAxis();
        NumberAxis yAxis = new NumberAxis();

        xAxis.setLabel("Iteration");
        yAxis.setLabel("Fitness");

        // Enable auto-ranging
        xAxis.setAutoRanging(true);
        yAxis.setAutoRanging(true);

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
    public void update(Number fitness, int iteration) {
        fitnessSeries.getData().add(new XYChart.Data<>(iteration, fitness));
    }

    public void reset() {
        fitnessSeries.getData().clear();
    }
}
