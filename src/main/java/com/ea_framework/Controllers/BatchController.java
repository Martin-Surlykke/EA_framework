package com.ea_framework.Controllers;
import com.ea_framework.*;
import com.ea_framework.Configs.AlgorithmConfigUI;
import com.ea_framework.Configs.BatchConfig;
import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Problems.Problem;
import com.ea_framework.Registries.Registry;
import com.ea_framework.SearchSpaces.SearchSpace;
import com.ea_framework.Termination.TerminationCondition;
import com.ea_framework.Views.ConfigViews.ConfigView;
import javafx.application.Platform;
import javafx.event.ActionEvent;
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
    @FXML private ComboBox<String> terminationModeCombo;
    @FXML private ComboBox<String> terminationDropDown;
    @FXML private ComboBox<String> searchSpaceDropDown;
    @FXML private ComboBox<String> problemDropDown;
    @FXML private ComboBox<String> fileDropDown;
    @FXML private ComboBox<String> algorithmDropDown;
    @FXML private TextField problemSize;
    @FXML private TextField batchSize;
    @FXML private TextField terminationValueField;
    @FXML private ListView<String> terminationListView;
    private final List<BatchStats> currentScheduleStats = new ArrayList<>();
    private final List<TerminationCondition> activeTerminations = new ArrayList<>();

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

        terminationDropDown.getItems().addAll(Registry.getAllTerminationConditions());

        terminationDropDown.valueProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null && newVal.toLowerCase().contains("threshold")) {
                terminationModeCombo.setDisable(false);
                terminationModeCombo.setPromptText("maximize/minimize");
            } else {
                terminationModeCombo.setDisable(true);
                terminationModeCombo.setPromptText("N/A");
            }
        });

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

        problemSize.setOnAction(e -> {
            if (problemInstanceSet() && algorithmTab.isDisable()) {
                algorithmTab.setDisable(false);
                tabPane.getSelectionModel().select(algorithmTab);
            }
        });

        problemSize.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus && problemInstanceSet() && algorithmTab.isDisable()) {
                algorithmTab.setDisable(false);
                tabPane.getSelectionModel().select(algorithmTab);
            }
        });

        terminationValueField.setOnAction(e -> handleAddTermination());
        terminationValueField.focusedProperty().addListener((obs, oldFocus, newFocus) -> {
            if (!newFocus) handleAddTermination();
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

        String sizeText = problemSize.getText();
        String stream = fileDropDown.getValue();

        if (sizeText != null && !sizeText.isBlank()) {
            // Use generated problem of specified size
            int size = Integer.parseInt(sizeText);
            config.setProblemSize(size); // You'll need to add this setter in BatchConfig
            config.setInputFile(null);   // No input file
            config.setStreamName(null);
        } else if (stream != null && !stream.isBlank()) {
            // Use predefined file
            config.setStreamName(stream);
            File file = ResourceLister.resolveProblemFile(config.getProblemName(), stream);
            config.setInputFile(file);
        }

        config.setAlgorithmName(algorithmDropDown.getValue());
        config.setAlgorithmDescriptor(Registry.getAlgorithmDescriptor(config.getAlgorithmName()));

        Problem problem = config.resolveProblem();
        config.setAlgorithmConfig(currentAlgoConfigUI.buildAlgorithmConfig(problem));

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
        addBatchButton.setDisable(!batchReady);
    }

    private void checkBatchForSwitch() {
        String value = batchSize.getText();
        boolean isValid = value != null && !value.isBlank();

        if (isValid && terminationTab.isDisable()) {
            try {
                Integer.parseInt(value);
                terminationTab.setDisable(false);
                tabPane.getSelectionModel().select(terminationTab);
                checkBatchReady();
            } catch (NumberFormatException ignored) {
            }
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
        config.getTerminationConditions().clear();
        config.getTerminationConditions().addAll(activeTerminations);
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
        terminationValueField.clear();
        terminationListView.getItems().clear();
        activeTerminations.clear();

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

        currentScheduleStats.clear();

        String timestamp = java.time.LocalDateTime.now()
                .toString().replace(":", "-");

        outputDir = new File("schedules/schedule_" + timestamp);
        outputDir.mkdirs();

        Platform.runLater(() -> {
            try {
                runSchedule(batches, 0);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    private void handleAddTermination() {
        String key = terminationDropDown.getValue();
        String param = terminationValueField.getText();
        String mode = terminationModeCombo.getValue();

        if (key == null || param.isBlank()) return;

        try {
            TerminationCondition condition = Registry.getTerminationCondition(key);

            if (key.toLowerCase().contains("threshold")) {
                if (mode == null || mode.isBlank()) {
                    return;
                }
                condition.configure(Map.of("value", param, "mode", mode));
                terminationListView.getItems().add(key + " = " + param + " (" + mode + ")");
            } else {
                condition.configure(Map.of("value", param));
                terminationListView.getItems().add(key + " = " + param);
            }

            activeTerminations.add(condition);
            terminationValueField.clear();
            terminationModeCombo.getSelectionModel().clearSelection();

            addTab.setDisable(false);
            tabPane.getSelectionModel().select(addTab);
            checkBatchReady();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void runSchedule(List<BatchConfig> batches, int index) throws Exception {
        if (index >= batches.size()) {
            return;
        }

        File dir = FrontPageController.getCsvSaveDirectory();

        currentScheduleStats.clear();

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
                String configKey = config.getProblemName() + "_" + config.getAlgorithmName();
                File summaryFile = new File(dir, String.format("schedule_summary_%02d_%s.csv", index, configKey));
                CSVStatWriter.writeScheduleSummary(currentScheduleStats, summaryFile);

                File fullSummary = new File(dir, "fullScheduleSummary.csv");
                CSVStatWriter.appendToFullScheduleSummary(fullSummary, summaryFile);

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

        RunBatch runBatch = new RunBatch(config, stage, returnScene, index, currentScheduleStats);
        runBatch.runAsync(() -> {
            try {
                runBatch(config, index + 1, stage, onDone);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void handleAddTermination(ActionEvent actionEvent) {
        handleAddTermination();
    }
}
