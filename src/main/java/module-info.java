module com.example.supplychainms {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;


    opens com.example.supplychainms to javafx.fxml;
    exports com.example.supplychainms;
}