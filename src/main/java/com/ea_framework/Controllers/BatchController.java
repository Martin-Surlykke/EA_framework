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
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class BatchController {
    // Batch controller
    // The main class for setting up batches in the schedule
    // Handles the config page with the associated TabPane containing
    // Configuration of Search space, Problem, Algorithm, Operators, Batches, Termination condition, and setVisual

    // Consists of a lot of handler classes designed to ensure invalid inputs are ignored, and a steady
    // Flow throught the program interface is achieved

    public Button addBatchButton;
    public ScheduleController scheduleController;
    public Button browseFileButton;
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

    // All FXML fields are defined
    private final List<TerminationCondition> activeTerminations = new ArrayList<>();

    @FXML private CheckBox showVisual;

    private File outputDir;
    private File userSelectedFile = null;

    private final List<BatchConfig> savedBatches = new ArrayList<>();
    private AlgorithmConfigUI currentAlgoConfigUI;


    // The initialize function handles setting up the different components

    // It loads the scheduleView which is used to hold batch cards, showing the added batches in the schedule
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

        // All tabs except searchspace are set disabled to ensure a user doesn't access an invalid combination of
        // search spaces, problems and algorithms
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

        // Listeners are added to fields to enable the program to "unlock" tabs when necessary information has
        // been filled in


        // A setCellFactory is used to list terminationCOnditions. This is done to ensure it is possible to delete
        // terminationconditions after adding them
        terminationListView.setCellFactory(listView -> new ListCell<>() {
            private final HBox content = new HBox(10);
            private final Label label = new Label();
            private final Button deleteButton = new Button("✕");

            {
                deleteButton.setStyle("-fx-text-fill: red; -fx-font-weight: bold;");
                deleteButton.setOnAction(e -> {
                    String item = getItem();
                    if (item != null) {
                        int index = getIndex();
                        terminationListView.getItems().remove(index);
                        activeTerminations.remove(index);  // Keep them in sync
                    }
                });
                content.getChildren().addAll(label, deleteButton);
            }


            // generic class for updating items
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    label.setText(item);
                    setGraphic(content);
                }
            }
        });
    }

    // handles the tab logic for after a search space has been chosen.
    // The valid problems are shown, and the tab is unlocked.
    public void onSearchSpaceSelected(String searchSpace) {
        problemTab.setDisable(false);
        tabPane.getSelectionModel().select(problemTab);
    }


    // onAlgorithmSelected handles the setup of the algorithm, a descriptor is created to
    // handle the setup with operaters / parameters etc.
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


    // Class used for storing the configurations made in a batch.
    // All configurations are stored in a BatchConfig class to be retrieved when needed for execution of the batch.
    private BatchConfig buildBatchConfigFromUI() throws IOException {
        BatchConfig config = new BatchConfig();

        config.setSearchSpaceName(searchSpaceDropDown.getValue());
        config.setSearchSpace(Registry.getSearchSpace(config.getSearchSpaceName()));

        config.setProblemName(problemDropDown.getValue());
        config.setProblemDescriptor(Registry.getProblem(config.getProblemName()).orElseThrow());

        String sizeText = problemSize.getText();
        String stream = fileDropDown.getValue();

        // Case 1: User entered size manually → generate problem of that size
        if (sizeText != null && !sizeText.isBlank()) {
            int size = Integer.parseInt(sizeText);
            config.setProblemSize(size);
            config.setInputFile(null);
            config.setStreamName(null);

            // Case 2: User selected a custom file
        } else if (userSelectedFile != null && stream != null && stream.startsWith("Custom: ")) {
            config.setProblemSize(-1); // optional: mark as file-based
            config.setInputFile(userSelectedFile);
            config.setStreamName(null); // don't use stream name

            // Case 3: Regular dropdown file selected
        } else if (stream != null && !stream.isBlank()) {
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


    // Handles adding a batch to the schedule. The batch config is created, and resetBatchUI is
    // called to reset the config page for a new batch to be set up.
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


    // Function which uses built in javaFX filechooser to allow the user to add tsp files namely.
    @FXML
    private void handleBrowseForFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Problem File");

        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Problem files", "*.txt", "*.tsp")
        );

        File selectedFile = fileChooser.showOpenDialog(tabPane.getScene().getWindow());

        if (selectedFile != null) {
            userSelectedFile = selectedFile;

            fileDropDown.getItems().add("Custom: " + selectedFile.getName());
            fileDropDown.getSelectionModel().select("Custom: " + selectedFile.getName());
        }
    }

    // handles selection of the search space
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

    // handles the selection of a problem. Algorithm dropdown is updated with valid algorithms.
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

    // Handles an algorithm being chosen
    @FXML
    private void handleAlgorithmSelect() {
        String selected = algorithmDropDown.getValue();
        if (selected != null) {
            onAlgorithmSelected(selected);
        }
    }


    // Handles the choosing one of the pre-added files from the framework.
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

    // checks if everything is filled out, before allowing the batch to be added to schedule
    private void checkBatchReady() {
        boolean batchReady = !batchSize.getText().isBlank();
        addBatchButton.setDisable(!batchReady);
    }


    // Opens the termination tab after batch has been filled in
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


    // disables all tabs except search space when a batch is added to the schedule
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

    // handles resetting all logic to factory setting, allowing the user to input a completely new batch
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


    // Runs the schedule. Takes each entry in the batches list and uses a Platform.runlater to run the schedule.
    // Terminates afetr the list is empty.
    @FXML
    private void handleRunSchedule() {
        List<BatchConfig> batches = scheduleController.getBatches();
        if (batches.isEmpty()) return;

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


    // Handles adding a termination, as more terminations can be applied. The terminations are stored as a list.
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

    // Class for running schedule, an iterative call of runBatch.
    // Also handles the logic of writing CSV stat / summary files for each batch
    private void runSchedule(List<BatchConfig> batches, int index) throws Exception {
        if (index >= batches.size()) {
            scheduleController.clearBatches();
            return;
        }

        File dir = FrontPageController.getCsvSaveDirectory();

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

        List<BatchStats> batchStats = new ArrayList<>();

        Stage stage = (Stage) tabPane.getScene().getWindow();
        runBatch(config, 0, stage, batchStats, () -> {
            try {
                String configKey = config.getProblemName() + "_" + config.getAlgorithmName();
                File summaryFile = new File(dir, String.format("schedule_summary_%02d_%s.csv", index, configKey));
                CSVStatWriter.writeScheduleSummary(batchStats, summaryFile);

                File fullSummary = new File(dir, "fullScheduleSummary.csv");
                CSVStatWriter.appendToFullScheduleSummary(fullSummary, summaryFile);

                runSchedule(batches, index + 1);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    // function for running a batch. Calls the class runBatch which then handles the actual run-logic
    private void runBatch(BatchConfig config, int index, Stage stage, List<BatchStats> stats, Runnable onDone) throws Exception {
        if (index >= config.getRepeats()) {
            onDone.run();

            return;
        }

        Scene returnScene = stage.getScene();
        RunBatch runBatch = new RunBatch(config, stage, returnScene, index, stats);
        runBatch.runAsync(() -> {
            try {
                runBatch(config, index + 1, stage, stats, onDone);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void handleAddTermination(ActionEvent actionEvent) {
        handleAddTermination();
    }

    // Functionality for leaving the batch config and returning to the main menu, when done,
    // the saved batches and terminations are cleared.
    @FXML
    private void handleMainMenu() {
        // Clear all batch-related state
        savedBatches.clear();
        activeTerminations.clear();

        if (scheduleController != null) {
            scheduleController.clearBatches();  // Also clears UI
        }

        // Reset form inputs
        resetBatchUI();

        // The stage is set to load the FrontPage (mainmenu) scene.
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/ea_framework/FrontPage.fxml"));
            Scene mainMenuScene = new Scene(loader.load());
            Stage stage = (Stage) tabPane.getScene().getWindow();
            stage.setScene(mainMenuScene);
            stage.setTitle("EA Framework - Main Menu");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
