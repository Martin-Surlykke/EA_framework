package com.ea_framework.View.CandidateView;
import com.ea_framework.Candidates.tspCandidate;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;

public class TspCandidateView implements CandidateView<tspCandidate> {

    private final Pane graphPane = new Pane();
    private final Pane edgeLayer = new Pane();
    private final Pane historyLayer = new Pane();
    private final Pane nodeLayer = new Pane();

    private final int [][] coordinateList;

    double xFactor;
    double yFactor;


    public TspCandidateView(tspCandidate tspCandidate) {
        graphPane.setPrefSize(700, 700);
        graphPane.getChildren().addAll(historyLayer, edgeLayer, nodeLayer);
        int maxX = tspCandidate.getMaxX();
        int maxY = tspCandidate.getMaxY();
        xFactor = (double) 750 / maxX;
        yFactor = (double) 550 / maxY;
        this.coordinateList = tspCandidate.getCoordinateList();
        drawNodes(graphPane, coordinateList, xFactor, yFactor);
    }

    @Override
    public Node getView() {
        return graphPane;
    }

    @Override
    public void update(tspCandidate candidate) {

        edgeLayer.getChildren().clear();

        int [] permutation = candidate.getPermutation();
        drawOldPath(permutation);
        drawCurrentPath(permutation);
    }

    private void drawCurrentPath(int[] permutation) {
        edgeLayer.getChildren().clear(); // ✅ remove old "current"

        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];

            int x1 = coordinateList[from][1];
            int y1 = coordinateList[from][2];
            int x2 = coordinateList[to][1];
            int y2 = coordinateList[to][2];

            Line line = new Line(x1 * xFactor, y1 * yFactor, x2 * xFactor, y2 * yFactor);
            line.setFill(Color.BLACK);
            line.setStroke(Color.BLACK);
            line.setStrokeWidth(3.0);
            edgeLayer.getChildren().add(line);
        }
    }

    private void drawOldPath(int[] permutation) {
        for (int i = 0; i < permutation.length; i++) {
            int from = permutation[i];
            int to = permutation[(i + 1) % permutation.length];

            int x1 = coordinateList[from][1];
            int y1 = coordinateList[from][2];
            int x2 = coordinateList[to][1];
            int y2 = coordinateList[to][2];

            Line line = new Line(x1 * xFactor, y1 * yFactor, x2 * xFactor, y2 * yFactor);
            line.setStroke(Color.LIGHTGRAY);
            line.setStrokeWidth(1.0);
            historyLayer.getChildren().add(line);
        }
    }


    public static void drawNodes(Pane nodeLayer,
                                 int [][] coordinateList,
                                 double xFactor,
                                 double yFactor) {

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

            // Center the text using bounds
            label.layoutBoundsProperty().addListener((obs, old, bounds) -> {
                label.setTranslateX(-bounds.getWidth() / 2.0);
                label.setTranslateY(+bounds.getHeight() / 4.0); // visually centers better
            });

            nodeLayer.getChildren().addAll(circle, label);
        }
    }
}
