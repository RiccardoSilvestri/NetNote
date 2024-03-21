module com.example.javaclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;


    opens com.example.javaclient to javafx.fxml;
    exports com.example.javaclient;
    exports com.example.javaclient.PackageTestingRiccardo;
    opens com.example.javaclient.PackageTestingRiccardo to javafx.fxml;
}