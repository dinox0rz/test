package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Address;
import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.domain.Factory;
import hr.javafx.production.devilla7.domain.Item;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import hr.javafx.production.devilla7.utils.ValidationUtils;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class AddNewFactoryScreenController {
    @FXML
    private TextField factoryNameTextField;
    @FXML
    private ComboBox<Address> factoryAddressComboBox;
    @FXML
    private ListView<Item> factoryItemsListView;

    public void initialize(){
        List<Address> addressList = DatabaseUtils.getAddressesFromDatabase();
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        List<Item> itemList = DatabaseUtils.getAllItemsFromDatabase(categoryList);
        ObservableList<Address> addressObservableList = FXCollections.observableArrayList(addressList);
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(itemList);
        factoryAddressComboBox.setItems(addressObservableList);
        factoryItemsListView.setItems(itemObservableList);
        factoryItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addNewFactory(){
        List<Category> categories = FileUtils.getCategoriesFromFile();
        List<Item> items = FileUtils.getItemsFromFile(categories);
        List<Address> addresses = FileUtils.getAddressesFromFile();
        List<Factory> factories = FileUtils.getFactoriesFromFile(items,addresses);
        List<String> errors = validateInput();
        if(errors.isEmpty()) {
            Long id = FileUtils.getNextFactoryId();
            String name = factoryNameTextField.getText();
            Address address = factoryAddressComboBox.getValue();
            ObservableList<Item> selectedItems = factoryItemsListView.getSelectionModel().getSelectedItems();
            Set<Item> selectedItemsSet = new HashSet<>(selectedItems);
            Factory newFactory = new Factory(id, name, address, selectedItemsSet);
            factories.add(newFactory);
            FileUtils.saveFactories(factories);
            showInformationDialog("Factory Added", "The factory has been successfully added!");

        } else {
            ValidationUtils.showValidationError(errors);
        }
    }

    private List<String> validateInput() {
        List<String> errors = new ArrayList<>();
        String factoryName = factoryNameTextField.getText().trim();
        if (factoryName.isEmpty()) {
            errors.add("Name cannot be empty");
        }

        // Validate address
        Address selectedAddress = factoryAddressComboBox.getValue();
        if (selectedAddress == null) {
            errors.add("Please select an address");
        }

        // Validate items
        ObservableList<Item> selectedItems = factoryItemsListView.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            errors.add("Please select at least one item");
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
