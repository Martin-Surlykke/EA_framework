package com.ea_framework.Controller;
import com.ea_framework.View.CandidateView.TspCandidateView;
import com.ea_framework.View.FitnessView.FitnessView;
import com.ea_framework.View.InfoViews.ConfigView;
import com.ea_framework.View.InfoViews.StatView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

public class RunController {

    @FXML
    public Pane visualizePane;

    @FXML
    public Pane fitnessPane;

    @FXML
    public Pane statPane;

    @FXML
    public Pane configPane;

    @FXML
    public Pane closePane;

    @FXML
    public Pane minimizePane;


    @FXML
    public void initialize(TspCandidateView tspCandidateView,
                           FitnessView fitnessView,
                           ConfigView configView,
                           StatView statView,
                           Stage stage) {

        fitToPane(tspCandidateView.getView(), visualizePane);
        fitToPane(fitnessView.getView(), fitnessPane);
        fitToPane(configView.getView(), configPane);
        fitToPane(statView.getView(), statPane);

        closePane.setOnMouseClicked(event -> {
            Platform.exit();
        });

        minimizePane.setOnMouseClicked(event -> {
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
