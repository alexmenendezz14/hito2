module com.empresa.hitoprogramacion {
    requires javafx.controls;
    requires javafx.fxml;
    requires mongo.java.driver;


    opens com.empresa.hitoprogramacion to javafx.fxml;
    exports com.empresa.hitoprogramacion;
}