module com.ea_framework {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.ea_framework to javafx.fxml;
    exports com.ea_framework;
    exports com.ea_framework.Candidates;
    exports com.ea_framework.StartAlgorithms;
    opens com.ea_framework.Candidates to javafx.fxml;
}