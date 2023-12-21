package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import hr.javafx.production.devilla7.utils.ValidationUtils;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;

import java.util.ArrayList;
import java.util.List;

public class AddNewCategoryScreenController {
    @FXML
    private TextField categoryNameTextField;
    @FXML
    private TextField categoryDescriptionTextField;

    public void addNewCategory(){
        List<String> errors = validateInput();
        if(errors.isEmpty()) {
            Long id = FileUtils.getNextCategoryId();
            String categoryName = categoryNameTextField.getText();
            String categoryDescription = categoryDescriptionTextField.getText();
            Category newCategory = new Category(id, categoryName, categoryDescription);
            List<Category> categoryList = FileUtils.getCategoriesFromFile();
            categoryList.add(newCategory);
            FileUtils.saveCategories(categoryList);
            DatabaseUtils.insertNewCategoryToDatabase(newCategory);
            showInformationDialog("Category Added", "The category has been successfully added!");
        } else {
            ValidationUtils.showValidationError(errors);
        }
    }

    private List<String> validateInput() {
        List<String> errors = new ArrayList<>();

        // Validate name
        String categoryName = categoryNameTextField.getText().trim();
        if (categoryName.isEmpty() || !categoryName.matches("[a-zA-Z0-9 ]+")) {
            errors.add("Name cannot be empty and must contain only alphanumeric characters or spaces.");
        }

        // Validate description
        String categoryDescription = categoryDescriptionTextField.getText().trim();
        if (categoryDescription.isEmpty()) {
            errors.add("Description cannot be empty.");
        }

        // Add more validation checks as needed

        return errors;
    }

    private void showInformationDialog(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
