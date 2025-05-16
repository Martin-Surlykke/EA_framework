package com.ea_framework.Views.VisualizeView;

import com.ea_framework.Configs.AlgorithmConfig;
import com.ea_framework.Configs.BitStringGenericAlgorithmConfig;
import com.ea_framework.Operators.FitnessFunctions.Fitness;
import com.ea_framework.Problems.BitStringProblem;
import com.ea_framework.Problems.Problem;
import javafx.geometry.Point2D;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CubicCurve;
import javafx.scene.shape.Line;

import java.util.ArrayList;
import java.util.List;

public class BitStringVisualizeView implements VisualizeView {

    private final StackPane root;
    private final Pane backgroundLayer;
    private final Pane pointLayer;
    private Fitness fitnessFunction;
    private final List<Point2D> history;
    private final boolean[] defaultBitString;
    private final int n;

    public BitStringVisualizeView(BitStringProblem problem) {
        this.backgroundLayer = new Pane();
        this.pointLayer = new Pane();
        this.root = new StackPane(backgroundLayer, pointLayer);
        this.history = new ArrayList<>();
        this.defaultBitString = problem.getDefaultPermutation();
        this.n = defaultBitString.length;

        root.setStyle("-fx-background-color: white;");

        backgroundLayer.prefWidthProperty().bind(root.widthProperty());
        backgroundLayer.prefHeightProperty().bind(root.heightProperty());
        pointLayer.prefWidthProperty().bind(root.widthProperty());
        pointLayer.prefHeightProperty().bind(root.heightProperty());

        root.widthProperty().addListener((obs, oldVal, newVal) -> drawBackground());
        root.heightProperty().addListener((obs, oldVal, newVal) -> drawBackground());

        drawBackground();
    }

    @Override
    public Node getView() {
        return root;
    }

    @Override
    public void update(Object solution) {
        if (!(solution instanceof boolean[] bitString) || fitnessFunction == null) return;
        this.currentSolution = bitString;

        Object raw = fitnessFunction.evaluate(currentSolution);
        double fitness = ((Number) raw).doubleValue();

        int spread = 0;
        for (int i = 0; i < currentSolution.length; i++) {
            if (currentSolution[i]) {
                spread += i;
            }
        }

        double min = ((fitness - 1.0) * fitness) / 2;
        double max = ((n - 1.0) * n) / 2 - ((n - 1.0 - fitness) * (n - fitness)) / 2;
        double range = max - min;

        double x = 2 * spread - 2 * min - range;

        double width = root.getWidth();
        double height = root.getHeight();

        double centerX = width / 2;
        double y = 20 + (((height - 40) * (n - fitness)) / n);

        double xOffset = (range == 0) ? 0 :
                (x * Math.sin(Math.PI * (y / height))) * (width * 0.4 / 2.0) / range;

        double xFitted = centerX + xOffset;

        history.add(new Point2D(xFitted, y));
        drawPoint(xFitted, y);
    }

    private void drawPoint(double x, double y) {
        Circle dot = new Circle(x, y, 3, Color.DODGERBLUE);
        pointLayer.getChildren().add(dot);
    }

    private void drawBackground() {
        backgroundLayer.getChildren().clear();

        double w = root.getWidth();
        double h = root.getHeight();
        if (w <= 0 || h <= 0) return;

        double padding = 20;
        double centerX = w / 2;

        for (int fitness = 1; fitness <= n; fitness += Math.max(1, n / 40)) {
            double min = (fitness - 1.0) * fitness / 2.0;
            double max = ((n - 1.0) * n) / 2.0 - ((n - 1.0 - fitness) * (n - fitness)) / 2.0;
            double range = max - min;
            double xSpan = (range == 0) ? 0 : (w * 0.4);

            double y = padding + ((h - 2 * padding) * (n - fitness)) / n;

            double leftX = centerX - xSpan / 2;
            double rightX = centerX + xSpan / 2;

            CubicCurve curve = new CubicCurve();
            curve.setStartX(leftX);
            curve.setStartY(y);
            curve.setControlX1(leftX);
            curve.setControlY1(y - 15);
            curve.setControlX2(rightX);
            curve.setControlY2(y - 15);
            curve.setEndX(rightX);
            curve.setEndY(y);
            curve.setStroke(Color.LIGHTGRAY);
            curve.setFill(Color.TRANSPARENT);

            backgroundLayer.getChildren().add(curve);
        }

        Line centerLine = new Line(centerX, padding, centerX, h - padding);
        centerLine.setStroke(Color.GRAY);
        centerLine.getStrokeDashArray().addAll(4.0, 4.0);
        backgroundLayer.getChildren().add(centerLine);
    }

    private boolean[] currentSolution;

    @Override
    public void applyConfig(AlgorithmConfig config) {
        if (config instanceof BitStringGenericAlgorithmConfig bitConfig) {
            this.fitnessFunction = bitConfig.fitness();
        }
    }
}
