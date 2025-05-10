package com.ea_framework.Controllers;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.AlgorithmControllers.GenericAlgorithmController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Registries.Registry;
import com.ea_framework.Views.ConfigView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class BatchController {

    public Button addBatchButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab searchSpaceTab;

    @FXML
    private Tab problemTab;

    @FXML
    private Tab algorithmTab;

    @FXML
    private Tab configTab;

    @FXML
    private Tab batchTab;

    @FXML
    private Tab terminationTab;

    @FXML
    private Tab addTab;

    @FXML
    private VBox scheduleBox;

    @FXML
    private ComboBox<String>  terminationDropDown;

    @FXML
    private ComboBox<String> searchSpaceDropDown;

    @FXML
    private ComboBox<String> problemDropDown;

    @FXML
    private ComboBox<String> fileDropDown;

    @FXML
    private ComboBox<String> algorithmDropDown;

    @FXML
    private TextField problemSize;

    @FXML
    private TextField batchSize;

    @FXML
    private TextField terminationSize;



    @FXML
    public void initialize() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);
        addTab.setDisable(true);


        // if this is just for results
        terminationDropDown.getItems().addAll(
                "Max iterations",
                "% fitness change",
                "Iterations without improvement"
        );

        terminationDropDown.setValue("Max iterations");

        searchSpaceDropDown.setPromptText("Select search space");
        problemDropDown.setPromptText("Select problem");
        algorithmDropDown.setPromptText("Select algorithm");

        searchSpaceDropDown.getItems().setAll(Registry.getAllSearchSpaces());
        problemDropDown.getItems().clear();
        algorithmDropDown.getItems().clear();

        problemDropDown.getItems().clear();
        algorithmDropDown.getItems().clear();


        currentConfig = new BatchConfig();

    }

    private final List<BatchConfig> savedBatches = new ArrayList<>();
    private BatchConfig currentConfig = new BatchConfig();
    private AlgorithmConfigUI currentAlgoConfigUI;
    private GenericAlgorithmController genericAlgorithmController;
    private ScheduleController scheduleController;

    public void setScheduleController(ScheduleController controller) {
        this.scheduleController = controller;
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

        AlgorithmDescriptor<?, ?> descriptor = Registry.getAlgorithmDescriptor(algorithmName);

        ConfigView view = descriptor.getConfigPage();
        AlgorithmConfigUI controller = view.getController();

        currentAlgoConfigUI = controller;

        if (controller instanceof GenericAlgorithmController gac) {
            genericAlgorithmController = gac;
        }

        configTab.setContent(view.getRoot());
        tabPane.getSelectionModel().select(configTab);
    }

    @FXML
    private void onAddToSchedule() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(false);
        terminationTab.setDisable(false);
        if (currentAlgoConfigUI == null) return;

        if (scheduleController != null) {
            scheduleController.addBatch(currentConfig);
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

        currentConfig = new BatchConfig(); // reset for next
        tabPane.getSelectionModel().select(searchSpaceTab);

        String terminationType = terminationDropDown.getValue();
        String terminationParam = terminationSize.getText();

        if (terminationType != null && !terminationParam.isBlank()) {
            currentConfig.getTerminationConfigs().put("type", terminationType);
            currentConfig.getTerminationConfigs().put("value", terminationParam);
        }

        String batchCount = batchSize.getText();
        if (batchCount != null && !batchCount.isBlank()) {
            currentConfig.getMetaConfigs().put("batchCount", batchCount);
        }

    }

    private void addBatchCard(BatchConfig config) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/ui/BatchCard.fxml"));
        Node card = loader.load();

        BatchCardController controller = loader.getController();
        controller.setBatch(config);
        controller.setOnEdit(this::loadConfig);          // reuse edit
        controller.setOnDelete(scheduleController::removeBatch);

        scheduleBox.getChildren().add(card);
    }

    public void loadConfig(BatchConfig config) {
        this.currentConfig = config;
        onAlgorithmSelected(config.getAlgorithm());

        javafx.application.Platform.runLater(() -> {
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

}
