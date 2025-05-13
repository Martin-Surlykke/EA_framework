module com.ea_framework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.naming;
    requires jdk.jdi;


    opens com.ea_framework to javafx.fxml;
    exports com.ea_framework;
    exports com.ea_framework.StartAlgorithms;
    exports com.ea_framework.Controllers;
    exports com.ea_framework.Configs;
    exports com.ea_framework.SearchSpaces;
    exports com.ea_framework.Algorithms;
    exports com.ea_framework.Descriptors;
    exports com.ea_framework.Loaders;
    exports com.ea_framework.Views.ConfigViews;
    exports com.ea_framework.Views.FitnessView;
    exports com.ea_framework.Views.InfoViews;
    exports com.ea_framework.Views.VisualizeView;
    exports com.ea_framework.Operators.FitnessFunctions;
    exports com.ea_framework.Operators.ChoiceFunctions;
    exports com.ea_framework.Operators.MutationFunctions;
    exports com.ea_framework.Filehandlers;
    exports com.ea_framework.Controllers.OperatorControllers;
    opens com.ea_framework.Loaders;
    opens com.ea_framework.Controllers to javafx.fxml;
    exports com.ea_framework.Registries;
    opens com.ea_framework.Registries to javafx.fxml;
    exports com.ea_framework.Problems;
    opens com.ea_framework.Problems to javafx.fxml;
    exports com.ea_framework.UIs;
    opens com.ea_framework.UIs to javafx.fxml;
    opens com.ea_framework.Controllers.AlgorithmControllers to javafx.fxml;
    opens com.ea_framework.Controllers.OperatorControllers to javafx.fxml;
    exports com.ea_framework.Runners;
    opens com.ea_framework.Runners to javafx.fxml;
    exports com.ea_framework.Configs.OperatorConfigs;
}