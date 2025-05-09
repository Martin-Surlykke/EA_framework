package com.ea_framework.Controller;

import com.ea_framework.Descriptors.AlgorithmDescriptor;
import com.ea_framework.Descriptors.ProblemDescriptor;
import com.ea_framework.Model.BatchRequest;
import com.ea_framework.Registries.AlgorithmRegistry;
import com.ea_framework.Registries.ProblemRegistry;
import com.ea_framework.Registries.SearchSpaceRegistry;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.io.IOException;
import java.util.List;
import java.util.Optional;


public class ScheduleController {

    @FXML
    ComboBox<String> searchSpaceDropDown;

    @FXML
    ComboBox<String> problemDropDown;

    @FXML
    ComboBox<String> algorithmDropDown;

    @FXML
    Label configLabel;

    @FXML
    Label batchesLabel;



    @FXML
    public void initialize() {
        searchSpaceDropDown.getItems().addAll(SearchSpaceRegistry.getRegisteredSearchSpaces());
        problemDropDown.getItems().addAll((ProblemRegistry.getAvailableProblems()));

        problemDropDown.setOnAction(event -> {
            String selectedProblem = problemDropDown.getValue();
            if (selectedProblem == null) {
                System.out.println("No problem selected");
                return;
            }
            String problemType = ProblemRegistry.getDescriptor(selectedProblem).problemType();
            List<String> matchingAlgorithms = AlgorithmRegistry.getAll().stream().
                     filter(a -> a.getProblemType().equals(problemType))
                    .map(AlgorithmDescriptor::getName)
                    .toList();

            algorithmDropDown.getItems().setAll(matchingAlgorithms);
        });

    }

    public Optional<BatchRequest> buildRequest() {
        if (searchSpaceDropDown.getValue()==null || problemDropDown.getValue()==null
                || algorithmDropDown.getValue()==null || batchesLabel.getText().isBlank())
            return Optional.empty();

        int its     = Integer.parseInt("1000");
        int repeats = Integer.parseInt(batchesLabel.getText());

        return Optional.of(new BatchRequest(
                searchSpaceDropDown.getValue(),
                problemDropDown.getValue(),
                algorithmDropDown.getValue(),
                its,
                repeats
        ));
    }


}
