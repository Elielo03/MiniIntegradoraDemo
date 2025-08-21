module com.example.school {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.bootstrapfx.core;
    requires org.xerial.sqlitejdbc;
    requires java.sql;
    requires opencv;

    opens com.example.school to javafx.fxml;
    opens com.example.school.controller to javafx.fxml;
    exports com.example.school;
}