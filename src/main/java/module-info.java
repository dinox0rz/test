module hr.javafx.production.devilla7 {
    requires javafx.controls;
    requires javafx.fxml;
    requires org.slf4j;
    requires java.sql;


    opens hr.javafx.production.devilla7 to javafx.fxml;
    exports hr.javafx.production.devilla7;
}