package com.ea_framework.View.CandidateView;

import com.ea_framework.Candidates.tspCandidate;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

public class TspCandidateView implements CandidateView<tspCandidate> {

    private final Pane graphPane = new Pane();
    private final Canvas historyCanvas = new Canvas(700, 700);
    private final Canvas edgeCanvas = new Canvas(700, 700);
    private final Pane nodeLayer = new Pane();

    private final int[][] coordinateList;

    private boolean [][] drawnLines;

    double xFactor;
    double yFactor;

    public TspCandidateView(tspCandidate tspCandidate) {
        int n = tspCandidate.getNodeCount();
        graphPane.setPrefSize(600, 600);
        graphPane.getChildren().addAll(historyCanvas, edgeCanvas, nodeLayer);
        drawnLines = new boolean[n][n];

        int maxX = tspCandidate.getMaxX();
        int maxY = tspCandidate.getMaxY();
        xFactor = (double) 550 / maxX;
        yFactor = (double) 550 / maxY;
        this.coordinateList = tspCandidate.getCoordinateList();
        drawNodes(nodeLayer, coordinateList, xFactor, yFactor);
    }

    @Override
    public Node getView() {
        return graphPane;
    }

    @Override
    public void update(tspCandidate candidate) {
        GraphicsContext edgeGC = edgeCanvas.getGraphicsContext2D();
        edgeGC.clearRect(0, 0, edgeCanvas.getWidth(), edgeCanvas.getHeight()); // Only clear edgeCanvas!

        int[] permutation = candidate.getPermutation();
        drawOldPath(permutation);
        drawCurrentPath(permutation);
    }

    private void drawCurrentPath(int[] permutation) {
        GraphicsContext gc = edgeCanvas.getGraphicsContext2D();
        gc.setStroke(Color.BLACK);
        gc.setLineWidth(3.0);

        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];

            double x1 = coordinateList[from][1] * xFactor;
            double y1 = coordinateList[from][2] * yFactor;
            double x2 = coordinateList[to][1] * xFactor;
            double y2 = coordinateList[to][2] * yFactor;

            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    private void drawOldPath(int[] permutation) {
        GraphicsContext gc = historyCanvas.getGraphicsContext2D();
        gc.setStroke(Color.LIGHTGRAY);
        gc.setLineWidth(1.0);



        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];

            if (!drawnLines[from][to]) {
                drawnLines[from][to] = true;
                drawnLines[to][from] = true;  // symmetry
                double x1 = coordinateList[from][1] * xFactor;
                double y1 = coordinateList[from][2] * yFactor;
                double x2 = coordinateList[to][1] * xFactor;
                double y2 = coordinateList[to][2] * yFactor;
                gc.strokeLine(x1, y1, x2, y2);
            }
        }
    }

    public static void drawNodes(Pane nodeLayer, int[][] coordinateList, double xFactor, double yFactor) {
        for (int[] node : coordinateList) {
            int index = node[0];
            int x = node[1];
            int y = node[2];

            double xScaled = x * xFactor;
            double yScaled = y * yFactor;

            Circle circle = new Circle(xScaled, yScaled, 10);
            circle.setFill(Color.LIGHTBLUE);
            circle.setStroke(Color.BLUE);
            Text label = new Text(String.valueOf(index));
            label.setX(xScaled);
            label.setY(yScaled);
            label.setStyle("-fx-font-size: 10px; -fx-font-weight: bold;");

            label.layoutBoundsProperty().addListener((obs, old, bounds) -> {
                label.setTranslateX(-bounds.getWidth() / 2.0);
                label.setTranslateY(+bounds.getHeight() / 4.0);
            });

            nodeLayer.getChildren().addAll(circle, label);
        }
    }
}
