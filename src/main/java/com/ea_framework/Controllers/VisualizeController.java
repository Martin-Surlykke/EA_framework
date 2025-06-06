package com.ea_framework.Controllers;

import com.ea_framework.Runners.Runner;
import com.ea_framework.Views.InfoViews.StatRecord;
import com.ea_framework.Views.FitnessView.FitnessView;
import com.ea_framework.Views.InfoViews.ConfigurationView;
import com.ea_framework.Views.InfoViews.StatView;
import com.ea_framework.Views.VisualizeView.VisualizeView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.control.Slider;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.IOException;

public class VisualizeController {

    private VisualizeView visualizeView;
    private FitnessView fitnessView;
    private StatView statView;

    @FXML public Pane visualizePane;
    @FXML public Pane fitnessPane;
    @FXML public Pane statPane;
    @FXML public Pane configPane;
    @FXML public Label nextRun;
    private Runnable onNextRun;

    @FXML public Slider speedSlider;
    @FXML private Polygon startShape;
    @FXML private Rectangle pauseBar1;
    @FXML private Rectangle pauseBar2;
    @FXML private Label statusLabel;
    @FXML private Label backToMainMenu;
    private boolean paused = false;

    private Runner runner;

    public void setRunner(Runner runner) {
        this.runner = runner;
    }

    @FXML
    private void handleBackToMainMenu() {
        if (runner != null) {
            runner.setPaused(true);
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/FrontPage.fxml"));
            Scene mainMenuScene = new Scene(loader.load());
            Stage stage = (Stage) backToMainMenu.getScene().getWindow();
            stage.setScene(mainMenuScene);
            stage.setTitle("EA Framework - Main Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handlePause() {
        pauseBar1.setFill(Color.GREY);
        pauseBar2.setFill(Color.GREY);
        startShape.setFill(Color.WHITE);
        statusLabel.setText("Paused");
        paused = true;
        if (runner != null) {
            runner.setPaused(paused);
        }
    }

    @FXML
    private void handleStart() {
        pauseBar1.setFill(Color.WHITE);
        pauseBar2.setFill(Color.WHITE);
        startShape.setFill(Color.GREY);
        statusLabel.setText("Running");
        paused = false;
        if (runner != null) {
            runner.setPaused(paused);
        }
    }

    public int getSleepDelay() {
        return 201 - (int) speedSlider.getValue();
    }

    public void enableNextRun() {
        Platform.runLater(() -> nextRun.setDisable(false));
    }

    @FXML
    public void initialize(VisualizeView visualizeView,
                           FitnessView fitnessView,
                           ConfigurationView configView,
                           StatView statView,
                           Stage stage) {

        this.visualizeView = visualizeView;
        this.fitnessView = fitnessView;
        this.statView = statView;

        if (visualizeView == null || visualizeView.getView() == null)
            System.err.println("Visualizer view or its node is null");
        if (fitnessView == null || fitnessView.getView() == null)
            System.err.println("Fitness view or its node is null");
        if (configView == null || configView.getView() == null)
            System.err.println("Config view or its node is null");
        if (statView == null || statView.getView() == null)
            System.err.println("Stat view or its node is null");

        assert visualizeView != null;
        fitToPane(visualizeView.getView(), visualizePane);
        assert fitnessView != null;
        fitToPane(fitnessView.getView(), fitnessPane);
        assert configView != null;
        fitToPane(configView.getView(), configPane);
        assert statView != null;
        fitToPane(statView.getView(), statPane);

    }


    public void updateAll(Object solution, int iteration, double fitness, StatRecord stat) {
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

    public void setOnNextRun(Runnable callback) {
        this.onNextRun = callback;
    }

    @FXML
    private void handleNextRun() {
        nextRun.setDisable(true);
        if (onNextRun != null) {
            onNextRun.run();
        }
    }



}
