package com.ea_framework.Controllers;

import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Controllers.AlgorithmControllers.GenericAlgorithmController;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Registries.Registry;
import com.ea_framework.ResourceLister;
import com.ea_framework.RunBatch;
import com.ea_framework.SearchSpaces.SearchSpace;
import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BatchController {

    public Button addBatchButton;
    public ScheduleController scheduleController;
    @FXML private AnchorPane scheduleContainer;
    @FXML private Label runSchedule;
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



    @FXML
    public void initialize() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/ScheduleView.fxml"));
            Node scheduleNode = loader.load();
            scheduleController = loader.getController();

            scheduleContainer.getChildren().add(scheduleNode);
            AnchorPane.setTopAnchor(scheduleNode, 0.0);
            AnchorPane.setBottomAnchor(scheduleNode, 0.0);
            AnchorPane.setLeftAnchor(scheduleNode, 0.0);
            AnchorPane.setRightAnchor(scheduleNode, 0.0);
        } catch (IOException e) {
            e.printStackTrace();
        }

        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);
        addTab.setDisable(true);

        addBatchButton.setDisable(true);

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

        tabPane.getSelectionModel().selectedItemProperty().addListener((obs, oldTab, newTab) -> {
            if (oldTab == problemTab && newTab != problemTab) {
                if (!problemInstanceSet()) {
                    tabPane.getSelectionModel().select(problemTab);
                } else {
                    algorithmTab.setDisable(false);
                }
            }
        });

        fileDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (problemInstanceSet() && algorithmTab.isDisable()) {
                algorithmTab.setDisable(false);
                tabPane.getSelectionModel().select(algorithmTab);
            }
        });

        problemSize.textProperty().addListener((obs, oldVal, newVal) -> {
            if (problemInstanceSet() && algorithmTab.isDisable()) {
                algorithmTab.setDisable(false);
                tabPane.getSelectionModel().select(algorithmTab);
            }
        });

        fileDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && !newVal.isBlank()) {
                currentConfig.setStreamName(newVal);
            }
        });
    }


    public void onSearchSpaceSelected(String searchSpace) {
        currentConfig.setSearchSpaceName(searchSpace);
        currentConfig.setSearchSpace(Registry.getSearchSpace(searchSpace))  ;
        problemTab.setDisable(false);
        tabPane.getSelectionModel().select(problemTab);
    }

    @FXML
    public void onAlgorithmSelected(String algorithmName) {
        configTab.setDisable(false);

        AlgorithmDescriptor descriptor = Registry.getAlgorithmDescriptor(algorithmName);
        ConfigView view = descriptor.getConfigPage();
        AlgorithmConfigUI controller = view.getController();
        currentConfig.setAlgorithmName(algorithmName);
        currentConfig.setAlgorithmDescriptor(descriptor);

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
    private void onAddToSchedule() throws FileNotFoundException {
        disableTabsAfterAdd();
        if (currentAlgoConfigUI == null) return;

        Map<String, Object> allOperators = new HashMap<>();
        if (currentAlgoConfigUI instanceof GenericAlgorithmController gac) {
            allOperators.putAll(gac.getOperatorInstances());
        }

        currentConfig.setRawOperatorConfigs(allOperators);

        populateTerminationConfig();

        String fullPath = "src/main/resources/tspFiles/st70.tsp";
        File file = new File(fullPath);
        if (!file.exists()) {
            System.err.println("File not found at: " + file.getAbsolutePath());
        } else {
            currentConfig.setInputFile(file);
            System.out.println(file.getAbsolutePath());
        }
        if (scheduleController != null) {
            scheduleController.addBatch(currentConfig);
            savedBatches.add(currentConfig);
            updateRunScheduleButton();
        }

        resetBatchUI();
    }



    @FXML
    private void handleSearchSpaceSelect() {
        String selected = searchSpaceDropDown.getValue();
        if (selected != null) {
            SearchSpace space = Registry.getSearchSpace(selected);

            if (space != null) {
                currentConfig.setSearchSpaceName(selected);
                currentConfig.setSearchSpace(space);

                onSearchSpaceSelected(selected);
                List<String> filteredProblems = Registry.getProblemsForSearchSpace(selected);
                problemDropDown.getItems().setAll(filteredProblems);
                problemDropDown.getSelectionModel().clearSelection();
                algorithmDropDown.getItems().clear();
            }
        }
    }

    @FXML
    private void handleProblemSelect() {
        String selected = problemDropDown.getValue();
        if (selected != null) {
            handleFileSelect(selected);
            List<String> filteredAlgorithms = Registry.getAlgorithmsForProblem(selected);
            algorithmDropDown.getItems().setAll(filteredAlgorithms);
            algorithmDropDown.getSelectionModel().clearSelection();
            ProblemDescriptor problemShell = Registry.getProblem(selected)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown problem: " + selected));
            currentConfig.setProblemDescriptor(problemShell);
            currentConfig.setProblemName(selected);
        }
    }

    private boolean problemInstanceSet() {
        String file = fileDropDown.getValue();
        String size = problemSize.getText();
        return (file != null && !file.isBlank()) || (size != null && !size.isBlank());
    }

    @FXML
    private void handleAlgorithmSelect() {
        String selected = algorithmDropDown.getValue();
        if (selected != null) {
            onAlgorithmSelected(selected);
        }
    }

    private void handleFileSelect(String problemType) {
        fileDropDown.getItems().clear();
        try {
            List<String> files = ResourceLister.listProblemFiles(problemType);
            if (files.isEmpty()) {
                fileDropDown.setPromptText("No files found");
            } else {
                for (String name : files) {
                    fileDropDown.getItems().add(name);
                }
                fileDropDown.setPromptText("Select file for " + problemType);
            }
        } catch (Exception e) {
            fileDropDown.setPromptText("Problem not supported");
            e.printStackTrace();
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
            currentConfig.setRepeats(Integer.parseInt(value));
            terminationTab.setDisable(false);
            tabPane.getSelectionModel().select(terminationTab);
            checkBatchReady();
        }
    }

    private void checkTerminationForSwitch() {
        String value = terminationSize.getText();
        boolean isValid = value != null && !value.isBlank();

        if (isValid) {
            currentConfig.setTermination(Integer.parseInt(value));
            addTab.setDisable(false);
            tabPane.getSelectionModel().select(addTab);
            checkBatchReady();
        }
    }

    private void disableTabsAfterAdd() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);
    }

    private void populateTerminationConfig() {
        String terminationType = terminationDropDown.getValue();
        String terminationParam = terminationSize.getText();
        if (terminationType != null && !terminationParam.isBlank()) {
            currentConfig.getTerminationConfigs().put("type", terminationType);
            currentConfig.getTerminationConfigs().put("value", terminationParam);
        }
    }

    private void populateMetaConfig() {
        String batchCount = batchSize.getText();
        if (batchCount != null && !batchCount.isBlank()) {
            currentConfig.getMetaConfigs().put("batchCount", batchCount);
        }

        if (currentAlgoConfigUI != null) {
            Map<String, Object> configMap = currentAlgoConfigUI.getConfigs();

            for (Map.Entry<String, Object> entry : configMap.entrySet()) {
                if (entry.getValue() != null) {
                    currentConfig.getMetaConfigs().put(entry.getKey(), entry.getValue().toString());
                }
            }
        }
    }

    private void resetBatchUI() {

        currentConfig = new BatchConfig();
        currentAlgoConfigUI = null;
        genericAlgorithmController = null;

        searchSpaceDropDown.getSelectionModel().clearSelection();
        problemDropDown.getSelectionModel().clearSelection();
        algorithmDropDown.getSelectionModel().clearSelection();
        fileDropDown.getSelectionModel().clearSelection();
        terminationDropDown.setValue("Max iterations");

        problemSize.clear();
        batchSize.clear();
        terminationSize.clear();

        configTab.setContent(null);

        disableTabsAfterAdd();

        tabPane.getSelectionModel().select(searchSpaceTab);

        addBatchButton.setDisable(true);
    }

    private void updateRunScheduleButton() {
        runSchedule.setDisable(savedBatches.isEmpty());
    }

    @FXML
    private void handleRunSchedule() throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        List<BatchConfig> batches = scheduleController.getBatches();

        if (batches.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Schedule Empty");
            alert.setHeaderText("No batches to run.");
            alert.setContentText("Please add at least one batch to the schedule.");
            alert.showAndWait();
            return;
        }

        for (BatchConfig config : batches) {
            if (config.getInputFile() == null) {
                try {
                    File file = ResourceLister.resolveProblemFile(config.getProblemName(), config.getStreamName());
                    config.setInputFile(file);
                } catch (Exception e) {
                    e.printStackTrace();
                    continue;
                }
            }
            runBatch(config);
        }
    }


    private void runBatch(BatchConfig config) throws IOException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Stage currentStage = (Stage) tabPane.getScene().getWindow();
        RunBatch runBatch = new RunBatch(config, currentStage);
        runBatch.run();
    }
}
