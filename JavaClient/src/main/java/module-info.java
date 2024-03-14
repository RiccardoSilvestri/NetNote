module com.example.javaclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens com.example.javaclient to javafx.fxml;
    exports com.example.javaclient;
    exports com.example.javaclient.LoginRegister;
    opens com.example.javaclient.LoginRegister to javafx.fxml;
}