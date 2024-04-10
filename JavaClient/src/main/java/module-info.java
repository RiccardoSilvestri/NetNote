module com.notenote.javaclient {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;
    requires org.json;
    requires jdk.management.agent;

    opens com.netnote.javaclient to javafx.fxml;
    exports com.netnote.javaclient;
    exports com.netnote.javaclient.PackageTestingRiccardo;
    opens com.netnote.javaclient.PackageTestingRiccardo to javafx.fxml;
}