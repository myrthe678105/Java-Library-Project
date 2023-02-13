module com.example.endassignment {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.Example.EndAssignment to javafx.fxml;
    exports com.Example.EndAssignment;
    exports com.Example.EndAssignment.Controller;
    exports com.Example.EndAssignment.Data;
    exports com.Example.EndAssignment.Model;

    opens com.Example.EndAssignment.Controller to javafx.fxml;
}