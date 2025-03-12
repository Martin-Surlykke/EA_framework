package com.ea_framework;

import com.ea_framework.Algorithms.simulatedAnnealing;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

import static com.ea_framework.tspHandler.*;

public class Window extends Application {


    @Override
    public void start(Stage stage) throws IOException {
        // Create a Circle

        int [][] edgeList;

        Pane root = new Pane();

        drawGraph(root);

        // Create a Scene with the root pane
        Scene scene = new Scene(root, 1020, 1020); // width 300, height 250

        // Set up the stage
        stage.setTitle("Simple Circle Drawer");
        stage.setScene(scene);
        stage.show(); // Display the stage
    }

    public static void main(String[] args) {
        handleTSP();

        factor = 1000 / getLargestVal(nodeList);
        System.out.println(Arrays.deepToString(edgeList));
        for (int i = 0; i < 50000; i++) {
            tspHandler.setEdgelist(simulatedAnnealing.anneal(edgeList, distanceMatrix));
        }

        launch();

    }

    public static void drawNode(Pane root, int index, int x, int y) {
            Circle circle = new Circle(x * factor, y * factor, 5, Color.BLUE); // Radius 10 and blue color
            root.getChildren().add(circle); // Add the circle to the root pane

            Text text = new Text(x * factor + 12, y * factor - 12, String.valueOf(index));
            text.setFill(Color.BLACK); // Set text color
            root.getChildren().add(text); // Add the text to the root pane
        }


    public static void drawLine(Pane root, int x1, int y1, int x2, int y2) {
        Line line = new Line(x1 * factor, y1 * factor, x2 * factor, y2 * factor);
        root.getChildren().add(line);

    }

    public static void drawGraph(Pane root) {
        int [][] list = tspHandler.getEdgeList();

        for (int i = 0; i < nodeList.size(); i++) {
            drawNode(root, nodeList.get(i)[0], nodeList.get(i)[1], nodeList.get(i)[2]);
        }

        for (int i = 0; i < list.length; i++) {
            drawLine(root, nodeList.get(list[i][0]-1)[1], nodeList.get(list[i][0]-1)[2], nodeList.get(list[i][1]-1)[1], nodeList.get(list[i][1]-1)[2]);
        }
    }

}

