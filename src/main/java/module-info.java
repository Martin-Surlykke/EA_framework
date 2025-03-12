module com.ea_framework {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.ea_framework to javafx.fxml;
    exports com.ea_framework;
}