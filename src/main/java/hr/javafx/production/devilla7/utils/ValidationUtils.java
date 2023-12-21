package hr.javafx.production.devilla7.utils;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextArea;

import java.util.List;

public class ValidationUtils {
    public static void showValidationError(List<String> errors) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Validation Error");
        alert.setHeaderText("Please correct the following errors:");

        TextArea textArea = new TextArea();
        textArea.setEditable(false);
        textArea.setWrapText(true);

        for (String error : errors) {
            textArea.appendText("- " + error + "\n");
        }

        alert.getDialogPane().setContent(textArea);
        alert.showAndWait();
    }
}
