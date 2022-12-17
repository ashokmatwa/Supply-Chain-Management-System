module com.example.supplychainms {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.supplychainms to javafx.fxml;
    exports com.example.supplychainms;
}