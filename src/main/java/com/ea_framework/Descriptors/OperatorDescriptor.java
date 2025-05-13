package com.ea_framework.Descriptors;

import com.ea_framework.Configurable;
import com.ea_framework.Controllers.OperatorControllers.OperatorConfigController;
import com.ea_framework.OperatorType;
import com.ea_framework.UIs.GenericOperatorUI;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;

import java.io.IOException;
import java.util.function.Supplier;

public class OperatorDescriptor<T extends Configurable<C>, C> {
    private final String name;
    private final OperatorType type;
    private final String fxmlPath;
    private final Supplier<GenericOperatorUI> uiSupplier;
    private final Supplier<T> operatorSupplier;  // NEW: supplier of actual logic

    public OperatorDescriptor(String name, OperatorType type, String fxmlPath,
                              Supplier<GenericOperatorUI> uiSupplier,
                              Supplier<T> operatorSupplier) {
        this.name = name;
        this.type = type;
        this.fxmlPath = fxmlPath;
        this.uiSupplier = uiSupplier;
        this.operatorSupplier = operatorSupplier;
    }

    public OperatorType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public GenericOperatorUI createUI() {
        return uiSupplier.get();
    }

    public GenericOperatorUI loadUI() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
        Parent root = loader.load();
        OperatorConfigController controller = loader.getController();
        return new GenericOperatorUI(root, controller);
    }

    public T createOperator(C config) {
        T operator = operatorSupplier.get();
        operator.configure(config); // ✅ no casting, no instanceof
        return operator;
    }
}