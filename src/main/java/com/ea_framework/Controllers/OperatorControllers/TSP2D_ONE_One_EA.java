package com.ea_framework.Controllers.OperatorControllers;

import com.ea_framework.Operators.MutationFunctions.TSP2D_One_One_EA;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class TSP2D_ONE_One_EA implements OperatorConfigController {

    @FXML private TextField lambdaField;

    private Runnable onChange = () -> {};

    @Override
    public boolean isFilled() {
        try {
            Double.parseDouble(lambdaField.getText());
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void setChangeListener(Runnable onChange) {
        this.onChange = onChange;
        lambdaField.textProperty().addListener((obs, oldVal, newVal) -> onChange.run());
    }

    @Override
    public Object getOperator() {
        double lambda = Double.parseDouble(lambdaField.getText());
        TSP2D_One_One_EA mutation = new TSP2D_One_One_EA();
        mutation.setLambda(lambda);
        return mutation;
    }
}
