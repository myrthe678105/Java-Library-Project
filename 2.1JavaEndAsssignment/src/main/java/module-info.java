module com.example.javaendasssignment {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;


    opens com.example.javaendasssignment to javafx.fxml;
    exports com.example.javaendasssignment;
    exports com.example.javaendasssignment.controller;
    opens com.example.javaendasssignment.controller to javafx.fxml;
    exports com.example.javaendasssignment.model;
    opens com.example.javaendasssignment.model to javafx.fxml;
    exports com.example.javaendasssignment.data;
    opens com.example.javaendasssignment.data to javafx.fxml;
}