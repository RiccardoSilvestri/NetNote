module com.example.javaclient {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.javaclient to javafx.fxml;
    exports com.example.javaclient;
}