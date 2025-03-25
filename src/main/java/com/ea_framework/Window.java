package com.ea_framework;

import com.ea_framework.Algorithms.AntColonyOptimization;
import com.ea_framework.Algorithms.RLS_bit;
import com.ea_framework.Algorithms.RLS_tsp;
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

    static double xFactor;
    static double yFactor;


    @Override
    public void start(Stage stage) throws IOException {
        // Create a Circle

        int [][] edgeList;

        Pane root = new Pane();

        drawGraph(root);

        // Create a Scene with the root pane
        Scene scene = new Scene(root, 750, 750);

        // Set up the stage
        stage.setTitle("Simple Circle Drawer");
        stage.setScene(scene);
        stage.show(); // Display the stage
    }

    public static void main(String[] args) {
        handleTSP();

         dimensions = getLargestVal(nodeList);
         xFactor = (double) 700 /dimensions[0];
         yFactor = (double) 700 /dimensions[1];

   /*      double [][] DM = tspHandler.getDistanceMatrix();
         int [][] input = tspHandler.getEdgeList();

         int [][] output = RLS_tsp.run_RLS(input, DM, 5000000);
        tspHandler.setEdgelist(output);

    */


        int [] bitString = RLS_bit.RLS_bitString(64,10000,"leadingOnes");
        System.out.println(Arrays.toString(bitString));




        launch();

    }

    public static void drawNode(Pane root, int index, int x, int y) {
            Circle circle = new Circle(x * xFactor, y * yFactor, 5, Color.BLUE); // Radius 10 and blue color
            root.getChildren().add(circle); // Add the circle to the root pane

            Text text = new Text(x * xFactor + 12, y * yFactor  - 12, String.valueOf(index));
            text.setFill(Color.BLACK); // Set text color
            root.getChildren().add(text); // Add the text to the root pane
        }


    public static void drawLine(Pane root, int x1, int y1, int x2, int y2) {
        Line line = new Line(x1 * xFactor , y1 * yFactor , x2 * xFactor , y2 * yFactor );
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

