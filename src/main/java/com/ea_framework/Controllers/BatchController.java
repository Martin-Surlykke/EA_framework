package com.ea_framework.Controllers;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.AlgorithmControllers.GenericAlgorithmController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Registries.Registry;
import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchController {

    public Button addBatchButton;
    @FXML private TabPane tabPane;
    @FXML private Tab searchSpaceTab;
    @FXML private Tab problemTab;
    @FXML private Tab algorithmTab;
    @FXML private Tab configTab;
    @FXML private Tab batchTab;
    @FXML private Tab terminationTab;
    @FXML private Tab addTab;

    @FXML private ComboBox<String> terminationDropDown;
    @FXML private ComboBox<String> searchSpaceDropDown;
    @FXML private ComboBox<String> problemDropDown;
    @FXML private ComboBox<String> fileDropDown;
    @FXML private ComboBox<String> algorithmDropDown;
    @FXML private TextField problemSize;
    @FXML private TextField batchSize;
    @FXML private TextField terminationSize;

    private final List<BatchConfig> savedBatches = new ArrayList<>();
    private BatchConfig currentConfig = new BatchConfig();
    private AlgorithmConfigUI currentAlgoConfigUI;
    private GenericAlgorithmController genericAlgorithmController;
    private ScheduleController scheduleController;


    @FXML
    public void initialize() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);
        addTab.setDisable(true);

        addBatchButton.setDisable(true);
        addBatchButton.setOnAction(e -> onAddToSchedule());

        terminationDropDown.getItems().addAll(
                "Max iterations"
        );

        searchSpaceDropDown.setPromptText("Select search space");
        problemDropDown.setPromptText("Select problem");
        algorithmDropDown.setPromptText("Select algorithm");

        searchSpaceDropDown.getItems().setAll(Registry.getAllSearchSpaces());
        problemDropDown.getItems().clear();
        algorithmDropDown.getItems().clear();

        currentConfig = new BatchConfig();

        batchSize.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus) checkBatchForSwitch();
        });
        batchSize.setOnAction(e -> checkBatchForSwitch());

        terminationSize.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus) checkBatchReady();
        });
        terminationSize.setOnAction(e -> checkTerminationForSwitch());
    }


    public void onSearchSpaceSelected(String searchSpace) {
        currentConfig.setSearchSpace(searchSpace);
        problemTab.setDisable(false);
        tabPane.getSelectionModel().select(problemTab);
    }

    public void onProblemSelected(String problem) {
        currentConfig.setProblem(problem);
        algorithmTab.setDisable(false);
        tabPane.getSelectionModel().select(algorithmTab);
    }

    @FXML
    public void onAlgorithmSelected(String algorithmName) {
        currentConfig.setAlgorithm(algorithmName);
        configTab.setDisable(false);

        AlgorithmDescriptor<?, ?> descriptor = Registry.getAlgorithmDescriptor(algorithmName);
        ConfigView view = descriptor.getConfigPage();
        AlgorithmConfigUI controller = view.getController();

        controller.bindTo(null);

        if (controller instanceof GenericAlgorithmController gac) {
            gac.setOnOperatorsFilled(() -> {
                System.out.println("All operators filled. Switching to batch tab.");
                batchTab.setDisable(false);
                tabPane.getSelectionModel().select(batchTab);
            });
        }
        currentAlgoConfigUI = controller;

        configTab.setContent(view.getRoot());
        tabPane.getSelectionModel().select(configTab);
    }

    @FXML
    private void onAddToSchedule() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);

        if (currentAlgoConfigUI == null) return;

        if (scheduleController != null) {
            scheduleController.addBatch(currentConfig); // Handles logic only
        }

        try {
            addBatchCard(currentConfig); // Handles UI
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (currentAlgoConfigUI != null) {
            currentConfig.getAlgorithmConfig().putAll(currentAlgoConfigUI.getConfigs());
        }
        if (genericAlgorithmController != null) {
            currentConfig.getAlgorithmConfig().putAll(genericAlgorithmController.getConfigs());
        }

        savedBatches.add(currentConfig);

        try {
            addBatchCard(currentConfig);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String terminationType = terminationDropDown.getValue();
        String terminationParam = terminationSize.getText();

        if (terminationType != null && !terminationParam.isBlank()) {
            currentConfig.getTerminationConfigs().put("type", terminationType);
            currentConfig.getTerminationConfigs().put("value", terminationParam);
        }

        currentConfig = new BatchConfig();
        tabPane.getSelectionModel().select(searchSpaceTab);

        String batchCount = batchSize.getText();
        if (batchCount != null && !batchCount.isBlank()) {
            currentConfig.getMetaConfigs().put("batchCount", batchCount);
        }

        batchSize.clear();
        terminationSize.clear();
        terminationDropDown.setValue("Max iterations");
        addBatchButton.setDisable(true);
    }

    private void addBatchCard(BatchConfig config) throws IOException {
        var url = getClass().getResource("/com/ea_framework/BatchCard.fxml");
        if (url == null) {
            throw new IllegalStateException("BatchCard.fxml not found at /com/ea_framework/BatchCard.fxml");
        }

        FXMLLoader loader = new FXMLLoader(url);
        Node card = loader.load();
        BatchCardController controller = loader.getController();
        controller.setBatch(config);
        controller.setOnEdit(this::loadConfig);

        controller.setTitle("Hello world");
        controller.setSummary("This is a summary");

        scheduleController.addBatch(config);
    }
    public void loadConfig(BatchConfig config) {
        this.currentConfig = config;
        onAlgorithmSelected(config.getAlgorithm());

        Platform.runLater(() -> {
            if (currentAlgoConfigUI != null) {
                currentAlgoConfigUI.loadConfigs(config.getAlgorithmConfig());
            }
            tabPane.getSelectionModel().select(configTab);
        });
    }

    @FXML
    private void handleSearchSpaceSelect() {
        String selected = searchSpaceDropDown.getValue();
        if (selected != null) {
            onSearchSpaceSelected(selected);
            List<String> filteredProblems = Registry.getProblemsForSearchSpace(selected);
            problemDropDown.getItems().setAll(filteredProblems);
            problemDropDown.getSelectionModel().clearSelection();
            algorithmDropDown.getItems().clear();
        }
    }

    @FXML
    private void handleProblemSelect() {
        String selected = problemDropDown.getValue();
        if (selected != null) {
            onProblemSelected(selected);
            List<String> filteredAlgorithms = Registry.getAlgorithmsForProblem(selected);
            algorithmDropDown.getItems().setAll(filteredAlgorithms);
            algorithmDropDown.getSelectionModel().clearSelection();
        }
    }

    @FXML
    private void handleAlgorithmSelect() {
        String selected = algorithmDropDown.getValue();
        if (selected != null) {
            onAlgorithmSelected(selected);
        }
    }

    private void checkBatchReady() {
        boolean batchReady = !batchSize.getText().isBlank();
        boolean terminationReady = !terminationSize.getText().isBlank();
        addBatchButton.setDisable(!(batchReady && terminationReady));
    }

    private void checkBatchForSwitch() {
        String value = batchSize.getText();
        boolean isValid = value != null && !value.isBlank();

        if (isValid) {
            terminationTab.setDisable(false);
            tabPane.getSelectionModel().select(terminationTab);
            checkBatchReady();
        }
    }

    private void checkTerminationForSwitch() {
        String value = terminationSize.getText();
        boolean isValid = value != null && !value.isBlank();

        if (isValid) {
            addTab.setDisable(false);
            tabPane.getSelectionModel().select(addTab);
            checkBatchReady();
        }
    }


    public void setScheduleController(ScheduleController scheduleController) {
        this.scheduleController = scheduleController;
        scheduleController.setOnEditRequested(this::loadConfig);
    }
}
