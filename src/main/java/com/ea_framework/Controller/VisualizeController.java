package com.ea_framework.Controller;
import com.ea_framework.Views.VisualizeView.TspVisualizeView;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigView;
import com.ea_framework.Views.InfoViews.StatView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class VisualizeController {

    @FXML
    public Pane visualizePane;

    @FXML
    public Pane fitnessPane;

    @FXML
    public Pane statPane;

    @FXML
    public Pane configPane;

    @FXML
    public Text closeWindow;

    @FXML
    public Rectangle minimizeWindow;


    @FXML
    public void initialize(TspVisualizeView tspCandidateView,
                           FitnessView fitnessView,
                           ConfigView configView,
                           StatView statView,
                           Stage stage) {

        fitToPane(tspCandidateView.getView(), visualizePane);
        fitToPane(fitnessView.getView(), fitnessPane);
        fitToPane(configView.getView(), configPane);
        fitToPane(statView.getView(), statPane);

        closeWindow.setOnMouseClicked(event -> {
            Platform.exit();
        });

        minimizeWindow.setOnMouseClicked(event -> {
            stage.setIconified(true);
        });
    }

    public void fitToPane(Node node, Pane pane) {
        Region region = (Region) node;
        region.prefWidthProperty().bind(pane.widthProperty());
        region.prefHeightProperty().bind(pane.heightProperty());
        pane.getChildren().add(region);
    }





}
