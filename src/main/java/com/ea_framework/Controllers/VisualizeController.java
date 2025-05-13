package com.ea_framework.Controllers;
import com.ea_framework.Algorithms.Algorithm;
import com.ea_framework.Views.InfoViews.StatRecord;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.VisualizeView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VisualizeController<S extends Algorithm<?>> {

    private VisualizeView<S> visualizeView;
    private FitnessView fitnessView;
    private StatView statView;

    @FXML public Pane visualizePane;
    @FXML public Pane fitnessPane;
    @FXML public Pane statPane;
    @FXML public Pane configPane;
    @FXML public Text closeWindow;
    @FXML public Rectangle minimizeWindow;

    @FXML
    public void initialize(VisualizeView<S> visualizeView,
                           FitnessView fitnessView,
                           ConfigurationView configView,
                           StatView statView,
                           Stage stage) {

        this.visualizeView = visualizeView;
        this.fitnessView = fitnessView;
        this.statView = statView;

        fitToPane(visualizeView.getView(), visualizePane);
        fitToPane(fitnessView.getView(), fitnessPane);
        fitToPane(configView.getView(), configPane);
        fitToPane(statView.getView(), statPane);

        closeWindow.setOnMouseClicked(event -> Platform.exit());
        minimizeWindow.setOnMouseClicked(event -> stage.setIconified(true));
    }

    public void updateAll(S solution, int iteration, double fitness, StatRecord stat) {
        visualizeView.update(solution);
        fitnessView.update(fitness, iteration);
        statView.update(stat);
    }

    private void fitToPane(Node node, Pane pane) {
        Region region = (Region) node;
        region.prefWidthProperty().bind(pane.widthProperty());
        region.prefHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(region);
    }
}


