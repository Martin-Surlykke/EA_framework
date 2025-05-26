package com.ea_framework.Controllers;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Registries.Registry;
import com.ea_framework.ResourceLister;
import com.ea_framework.RunBatch;
import com.ea_framework.SearchSpaces.SearchSpace;
import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
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

    @FXML private CheckBox showVisual;

    private File outputDir;
    private File scheduleSummary;

    private final List<BatchConfig> savedBatches = new ArrayList<>();
    private AlgorithmConfigUI currentAlgoConfigUI;



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
    }


    public void onSearchSpaceSelected(String searchSpace) {
        problemTab.setDisable(false);
        tabPane.getSelectionModel().select(problemTab);
    }

    @FXML
    public void onAlgorithmSelected(String algorithmName) {
        configTab.setDisable(false);

        AlgorithmDescriptor descriptor = Registry.getAlgorithmDescriptor(algorithmName);
        ConfigView view = descriptor.getConfigPage();
        AlgorithmConfigUI controller = view.getController();

        controller.bindTo(null);

        controller.setOnReady(() -> {
            System.out.println("Config ready. Switching to batch tab.");
            batchTab.setDisable(false);
            tabPane.getSelectionModel().select(batchTab);
        });
        currentAlgoConfigUI = controller;

        configTab.setContent(view.getRoot());
        tabPane.getSelectionModel().select(configTab);
    }

    private BatchConfig buildBatchConfigFromUI() throws IOException {
        BatchConfig config = new BatchConfig();

        config.setSearchSpaceName(searchSpaceDropDown.getValue());
        config.setSearchSpace(Registry.getSearchSpace(config.getSearchSpaceName()));

        config.setProblemName(problemDropDown.getValue());
        config.setProblemDescriptor(Registry.getProblem(config.getProblemName()).orElseThrow());

        config.setStreamName(fileDropDown.getValue());

        File file = ResourceLister.resolveProblemFile(config.getProblemName(), config.getStreamName());
        config.setInputFile(file);

        config.setAlgorithmName(algorithmDropDown.getValue());
        config.setAlgorithmDescriptor(Registry.getAlgorithmDescriptor(config.getAlgorithmName()));

        Problem problem = config.resolveProblem();
        config.setAlgorithmConfig(currentAlgoConfigUI.buildAlgorithmConfig(problem));

        config.setTermination(Integer.parseInt(terminationSize.getText()));
        config.setRepeats(Integer.parseInt(batchSize.getText()));
        config.setVisualSelected(showVisual.isSelected());

        populateTerminationConfig(config);
        populateMetaConfig(config);

        return config;
    }

    @FXML
    private void onAddToSchedule() {
        disableTabsAfterAdd();
        if (currentAlgoConfigUI == null) return;

        try {
            BatchConfig config = buildBatchConfigFromUI();
            scheduleController.addBatch(config);
            savedBatches.add(config);
            updateRunScheduleButton();
            resetBatchUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void handleSearchSpaceSelect() {
        String selected = searchSpaceDropDown.getValue();
        if (selected != null) {
            SearchSpace space = Registry.getSearchSpace(selected);

            if (space != null) {
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

    private void disableTabsAfterAdd() {
        problemTab.setDisable(true);
        algorithmTab.setDisable(true);
        configTab.setDisable(true);
        batchTab.setDisable(true);
        terminationTab.setDisable(true);
    }

    private void populateTerminationConfig(BatchConfig config) {
        String terminationType = terminationDropDown.getValue();
        String terminationParam = terminationSize.getText();
        if (terminationType != null && !terminationParam.isBlank()) {
            config.getTerminationConfigs().put("type", terminationType);
            config.getTerminationConfigs().put("value", terminationParam);
        }
    }


    private void populateMetaConfig(BatchConfig config) {
        String batchCount = batchSize.getText();
        if (batchCount != null && !batchCount.isBlank()) {
            config.getMetaConfigs().put("batchCount", batchCount);
        }

        if (currentAlgoConfigUI != null) {
            Map<String, Object> configMap = currentAlgoConfigUI.getConfigs();
            for (Map.Entry<String, Object> entry : configMap.entrySet()) {
                if (entry.getValue() != null) {
                    config.getMetaConfigs().put(entry.getKey(), entry.getValue().toString());
                }
            }
        }
    }


    private void resetBatchUI() {


        currentAlgoConfigUI = null;

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
    private void handleRunSchedule() {
        List<BatchConfig> batches = scheduleController.getBatches();
        if (batches.isEmpty()) return;

        String timestamp = java.time.LocalDateTime.now()
                .toString().replace(":", "-");

        outputDir = new File("schedules/schedule_" + timestamp);
        outputDir.mkdirs();

        scheduleSummary = new File(outputDir, "schedule_summary.csv");

        Platform.runLater(() -> {
            try {
                runSchedule(batches, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    private void runSchedule(List<BatchConfig> batches, int index) throws Exception {
        if (index >= batches.size()) return;

        BatchConfig config = batches.get(index);
        if (config.getInputFile() == null) {
            try {
                File file = ResourceLister.resolveProblemFile(config.getProblemName(), config.getStreamName());
                config.setInputFile(file);
            } catch (Exception e) {
                e.printStackTrace();
                runSchedule(batches, index + 1);
                return;
            }
        }

        Stage stage = (Stage) tabPane.getScene().getWindow();
        runBatch(config, 0, stage, () -> {
            try {
                runSchedule(batches, index + 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    private void runBatch(BatchConfig config, int index, Stage stage, Runnable onDone) throws Exception {
        if (index >= config.getRepeats()) {
            onDone.run();
            return;
        }

        Scene returnScene = stage.getScene();

        RunBatch runBatch = new RunBatch(config, stage, returnScene, index, outputDir, scheduleSummary);
        runBatch.runAsync(() -> {
            try {
                runBatch(config, index + 1, stage, onDone);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }
}
