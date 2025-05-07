package com.ea_framework.View.CandidateView;

import com.ea_framework.Candidates.tspCandidate;
import com.ea_framework.Coordinate;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Text;

import java.util.List;

public class TspCandidateView implements CandidateView<tspCandidate> {

    private final Pane graphPane = new Pane();
    private final Canvas historyCanvas = new Canvas(700, 700);
    private final Canvas edgeCanvas = new Canvas(700, 700);
    private final Pane nodeLayer = new Pane();

    private final List<Coordinate> coordinateList;

    private final int [][] drawnLines;

    double xFactor;
    double yFactor;

    private static final double NODE_RADIUS = 10.0;
    private static final double PADDING = NODE_RADIUS * 2;

    double minX;
    double minY;

    public TspCandidateView(tspCandidate tspCandidate) {
        int n = tspCandidate.getNodeCount();
        graphPane.setPrefSize(600, 600);
        graphPane.getChildren().setAll(historyCanvas, edgeCanvas, nodeLayer);
        drawnLines = new int [n][n];

        double maxX = tspCandidate.getMaxX();
        double maxY = tspCandidate.getMaxY();

        minX = tspCandidate.getMinX();
        minY = tspCandidate.getMinY();

        graphPane.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            xFactor = (newWidth.doubleValue()- PADDING) / maxX;
            redrawNodes();
        });
        graphPane.heightProperty().addListener((obs, oldHeight, newHeight) -> {
            yFactor = (newHeight.doubleValue()- PADDING) / maxY;
            redrawNodes();
        });
        this.coordinateList = tspCandidate.getCoordinateList();
        drawNodes(nodeLayer, coordinateList, xFactor, yFactor, minX, minY);
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
        gc.setStroke(Color.RED);
        gc.setGlobalAlpha(0.5);
        gc.setLineWidth(3.0);

        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];

            double x1 = (coordinateList.get(from).x() - minX) * xFactor + NODE_RADIUS;
            double y1 = (coordinateList.get(from).y() - minY) * yFactor + NODE_RADIUS;
            double x2 = (coordinateList.get(to).x() - minX) * xFactor + NODE_RADIUS;
            double y2 = (coordinateList.get(to).y() - minY) * yFactor + NODE_RADIUS;


            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    private void drawOldPath(int[] permutation) {
        GraphicsContext gc = historyCanvas.getGraphicsContext2D();
        gc.setStroke(Color.gray(0.6));
        gc.setLineWidth(1.0);



        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];
             
                drawnLines[from][to]++;
                drawnLines[to][from]++;  // symmetry
                double x1 = (coordinateList.get(from).x() - minX) * xFactor + NODE_RADIUS;
                double y1 = (coordinateList.get(from).y() - minY) * yFactor + NODE_RADIUS;
                double x2 = (coordinateList.get(to).x() - minX) * xFactor + NODE_RADIUS;
                double y2 = (coordinateList.get(to).y() - minY) * yFactor + NODE_RADIUS;

            int count = drawnLines[from][to];
            double alpha = Math.min(0.05, count * 0.01);
            gc.setGlobalAlpha(alpha);

            gc.strokeLine(x1, y1, x2, y2);
        }
    }

    public static void drawNodes(Pane nodeLayer, List<Coordinate> coordinateList, double xFactor, double yFactor, double minX, double minY) {
        for (Coordinate coordinate : coordinateList) {
            int index = coordinate.id();
            double x = coordinate.x();
            double y = coordinate.y();

            double xScaled = (x - minX) * xFactor+ NODE_RADIUS;
            double yScaled = (y - minY) * yFactor + NODE_RADIUS;

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


    private void redrawNodes() {
        nodeLayer.getChildren().clear();
        drawNodes(nodeLayer, coordinateList, xFactor, yFactor, minX, minY);
    }

}
