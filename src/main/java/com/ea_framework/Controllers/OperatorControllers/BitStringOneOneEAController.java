package com.ea_framework.Controllers.OperatorControllers;
import com.ea_framework.Operators.MutationFunctions.BitStringOneOneEA;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class BitStringOneOneEAController implements OperatorConfigController {

    // Controller class for (1+1)EA for bitstrings, a variable x is introduced.
    // P = x/n
    @FXML
    private TextField xField;

    @Override
    public Object getOperator() {
        BitStringOneOneEA operator = new BitStringOneOneEA();
        try {
            int x = Integer.parseInt(xField.getText());
            operator.setX(x);
        } catch (NumberFormatException e) {
            System.err.println("Invalid x value; using default 1");
        }
        return operator;
    }

    @Override
    public boolean isFilled() {
        return xField != null && !xField.getText().isEmpty();
    }
}
