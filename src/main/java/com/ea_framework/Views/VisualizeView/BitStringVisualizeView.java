package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Problems.BitStringCompatible;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.util.Arrays;

public class BitStringVisualizeView implements VisualizeView {

    private final StackPane root;
    private final Pane backgroundLayer;
    private final Pane pointLayer;
    private final int n;

    public BitStringVisualizeView(BitStringCompatible problem) {
        this.n = problem.getDefaultPermutation().length;

        this.backgroundLayer = new Pane();
        this.pointLayer = new Pane();
        this.root = new StackPane(backgroundLayer, pointLayer);
        root.setStyle("-fx-background-color: white;");

        backgroundLayer.prefWidthProperty().bind(root.widthProperty());
        backgroundLayer.prefHeightProperty().bind(root.heightProperty());
        pointLayer.prefWidthProperty().bind(root.widthProperty());
        pointLayer.prefHeightProperty().bind(root.heightProperty());

        root.widthProperty().addListener((obs, oldVal, newVal) -> drawBounds());
        root.heightProperty().addListener((obs, oldVal, newVal) -> drawBounds());

        drawBounds();
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void update(Object solution) {
        if (!(solution instanceof boolean[] bits)) return;

        double width = root.getWidth();
        double height = root.getHeight();
        double padding = 20;
        double usableHeight = height - 2 * padding;

        int ones = countOnes(bits);
        double spread = computeSpread(bits);

        int[] minMax = minMaxSpread(ones);
        double xRel = (minMax[1] - minMax[0]) == 0 ? 0.5 : (spread - minMax[0]) / (double)(minMax[1] - minMax[0]);

        double yNorm = ones / (double) n;
        double shapingFactor = Math.exp(-Math.pow((yNorm - 0.5) * 4, 2));

        double x = width / 2 + (xRel - 0.5) * (width * 0.9) * shapingFactor;
        double y = padding + (1 - yNorm) * usableHeight;

        drawPoint(x, y, Color.RED, 3);
    }

    @Override
    public void applyConfig(AlgorithmConfig config) {
    }

    private void drawBounds() {
        backgroundLayer.getChildren().clear();
        pointLayer.getChildren().clear();

        double width = root.getWidth();
        double height = root.getHeight();
        double padding = 20;
        double usableHeight = height - 2 * padding;

        for (int ones = 0; ones <= n; ones++) {

            double yNorm = ones / (double) n;
            double y = padding + (1 - yNorm) * usableHeight;
            double shapingFactor = Math.exp(-Math.pow((yNorm - 0.5) * 4, 2));

            double xLeft = width / 2 - 0.5 * (width * 0.9) * shapingFactor;
            double xRight = width / 2 + 0.5 * (width * 0.9) * shapingFactor;

            Line envelopeLine = new Line(xLeft, y, xRight, y);
            envelopeLine.setStroke(Color.LIGHTGRAY);
            backgroundLayer.getChildren().add(envelopeLine);
        }

        double centerX = width / 2;
        Line centerLine = new Line(centerX, padding, centerX, height - padding);
        centerLine.setStroke(Color.GRAY);
        centerLine.getStrokeDashArray().addAll(4.0, 4.0);
        backgroundLayer.getChildren().add(centerLine);
    }

    private double computeSpread(boolean[] bits) {
        double sum = 0;
        for (int i = 0; i < bits.length; i++) {
            if (bits[i]) sum += i;
        }
        return sum;
    }

    private int countOnes(boolean[] bits) {
        int count = 0;
        for (boolean b : bits) if (b) count++;
        return count;
    }

    private int[] minMaxSpread(int ones) {
        int min = 0;
        for (int i = 0; i < ones; i++) min += i;

        int max = 0;
        for (int i = n - ones; i < n; i++) max += i;

        return new int[]{min, max};
    }

    private void drawPoint(double x, double y, Color color, double radius) {
        Circle dot = new Circle(x, y, radius, color);
        pointLayer.getChildren().add(dot);
    }
}
