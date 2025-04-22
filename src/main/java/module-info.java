module org.sessionproject.ejednevnik {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;

    opens org.sessionproject.ejednevnik to javafx.fxml;
    exports org.sessionproject.ejednevnik;
}