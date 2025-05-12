module com.ea_framework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires java.naming;


    opens com.ea_framework to javafx.fxml;
    exports com.ea_framework;
    exports com.ea_framework.StartAlgorithms;
    exports com.ea_framework.Controllers;
    exports com.ea_framework.Configs;
    exports com.ea_framework.SearchSpaces;
    exports com.ea_framework.Algorithms;
    exports com.ea_framework.Descriptors;
    exports com.ea_framework.Loaders;
    exports com.ea_framework.FitnessFunctions;
    exports com.ea_framework.ChoiceFunctions;
    exports com.ea_framework.MutationFunctions;
    exports com.ea_framework.Filehandlers;
    exports com.ea_framework.Model;
    opens com.ea_framework.Loaders;
    opens com.ea_framework.Controllers to javafx.fxml;
    exports com.ea_framework.Registries;
    opens com.ea_framework.Registries to javafx.fxml;
    exports com.ea_framework.Problems;
    opens com.ea_framework.Problems to javafx.fxml;
    exports com.ea_framework.UIs;
    opens com.ea_framework.UIs to javafx.fxml;
    exports com.ea_framework.Views.ConfigViews;
    opens com.ea_framework.Controllers.AlgorithmControllers to javafx.fxml;
    opens com.ea_framework.Controllers.OperatorControllers to javafx.fxml;
}