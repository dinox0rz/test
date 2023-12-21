package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.domain.Discount;
import hr.javafx.production.devilla7.domain.Item;
import hr.javafx.production.devilla7.enumeration.City;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import hr.javafx.production.devilla7.utils.ValidationUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.io.File;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AddNewItemScreenController {
    @FXML
    private TextField itemNameTextField;
    @FXML
    private ComboBox<Category> itemCategoryComboBox;
    @FXML
    private TextField itemWidthTextField;
    @FXML
    private TextField itemHeightTextField;
    @FXML
    private TextField itemLengthTextField;
    @FXML
    private TextField itemProductionCostTextField;
    @FXML
    private TextField itemSellingPriceTextField;

    public void initialize(){
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        ObservableList<Category> categoryObservableList = FXCollections.observableArrayList(categoryList);
        itemCategoryComboBox.setItems(categoryObservableList);
    }

    public void addNewItem(){
        List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Item> itemList = FileUtils.getItemsFromFile(categoryList);
        List<String> errors = validateInput();
        if(errors.isEmpty()) {
            Long id = FileUtils.getNextItemId();
            String itemName = itemNameTextField.getText();
            Category category = itemCategoryComboBox.getValue();
            String widthString = itemWidthTextField.getText();
            String heightString = itemHeightTextField.getText();
            String lengthString = itemLengthTextField.getText();
            BigDecimal width = new BigDecimal(widthString);
            BigDecimal height = new BigDecimal(heightString);
            BigDecimal length = new BigDecimal(lengthString);
            String productionCostString = itemProductionCostTextField.getText();
            String sellingPriceString = itemSellingPriceTextField.getText();
            BigDecimal productionCost = new BigDecimal(productionCostString);
            BigDecimal sellingPrice = new BigDecimal(sellingPriceString);
            Item newItem = new Item(id, itemName, category, width, height, length, productionCost, sellingPrice, new Discount(0));
            itemList.add(newItem);
            FileUtils.saveItems(itemList);
            DatabaseUtils.insertNewItemToDatabase(newItem);
            showInformationDialog("Item Added", "The item has been successfully added!");
        } else {
            ValidationUtils.showValidationError(errors);
        }
    }

    private List<String> validateInput() {
        List<String> errors = new ArrayList<>();
        // Validate Name
        String itemName = itemNameTextField.getText();
        if (itemName.isEmpty()) {
            errors.add("Name cannot be empty");
        }

        // Validate Category
        Category selectedCategory = itemCategoryComboBox.getValue();
        if (selectedCategory == null) {
            errors.add("Please select a category");
        }

        // Validate Width, Height, Length
        try {
            BigDecimal width = new BigDecimal(itemWidthTextField.getText());
            BigDecimal height = new BigDecimal(itemHeightTextField.getText());
            BigDecimal length = new BigDecimal(itemLengthTextField.getText());

            // Additional validation for non-negative values if needed
            if (width.compareTo(BigDecimal.ZERO) < 0 || height.compareTo(BigDecimal.ZERO) < 0 || length.compareTo(BigDecimal.ZERO) < 0) {
                errors.add("Width, height, and length must be non-negative values");
            }
        } catch (NumberFormatException e) {
            errors.add("Width, height, and length must be valid decimal numbers");
        }

        // Validate Production Cost and Selling Price
        try {
            BigDecimal productionCost = new BigDecimal(itemProductionCostTextField.getText());
            BigDecimal sellingPrice = new BigDecimal(itemSellingPriceTextField.getText());

            // Additional validation for non-negative values if needed
            if (productionCost.compareTo(BigDecimal.ZERO) < 0 || sellingPrice.compareTo(BigDecimal.ZERO) < 0) {
                errors.add("Production cost and selling price must be non-negative values");
            }
        } catch (NumberFormatException e) {
            errors.add("Production cost and selling price must be valid decimal numbers");
        }
        return errors;
    }
    private void showInformationDialog(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
