module com.example.farmacia {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.farmacia to javafx.fxml;
    exports com.example.farmacia;
}