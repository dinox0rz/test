package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.*;
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

public class AddNewStoreScreenController {
    @FXML
    private TextField storeNameTextField;
    @FXML
    private TextField storeWebAddressTextField;
    @FXML
    private ComboBox<Address> storeAddressComboBox;
    @FXML
    private ListView<Item> storeItemsListView;
    @FXML RadioButton storeHoursRadioButtonYes;
    @FXML RadioButton storeHoursRadioButtonNo;
    @FXML ColorPicker storeColorPicker;

    public void initialize(){
        List<Address> addressList = FileUtils.getAddressesFromFile();
        List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Item> itemList = FileUtils.getItemsFromFile(categoryList);
        ObservableList<Address> addressObservableList = FXCollections.observableArrayList(addressList);
        ObservableList<Item> itemObservableList = FXCollections.observableArrayList(itemList);
        storeAddressComboBox.setItems(addressObservableList);
        storeItemsListView.setItems(itemObservableList);
        storeItemsListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
    }

    public void addNewStore(){
        List<Category> categories = FileUtils.getCategoriesFromFile();
        List<Item> items = FileUtils.getItemsFromFile(categories);
        List<Address> addresses = FileUtils.getAddressesFromFile();
        List<Store> stores = FileUtils.getStoresFromFile(items,addresses);
        List<String> errors = validateInput();
        if(errors.isEmpty()) {
            Long id = FileUtils.getNextStoreId();
            String name = storeNameTextField.getText();
            String webAddress = storeWebAddressTextField.getText();
            Address address = storeAddressComboBox.getValue();
            ObservableList<Item> selectedItems = storeItemsListView.getSelectionModel().getSelectedItems();
            Set<Item> selectedItemsSet = new HashSet<>(selectedItems);
            Store newStore = new Store(id, name, webAddress, address, selectedItemsSet);
            stores.add(newStore);
            FileUtils.saveStores(stores);
            showInformationDialog("Store Added", "The store has been successfully added!");

        } else {
            ValidationUtils.showValidationError(errors);
        }
    }

    private List<String> validateInput() {
        List<String> errors = new ArrayList<>();
        String storeName = storeNameTextField.getText();
        if (storeName.trim().isEmpty()) {
            errors.add("Store name cannot be empty");
        }

        // Validate web address
        String webAddress = storeWebAddressTextField.getText();
        if (webAddress.trim().isEmpty()) {
            errors.add("Web address cannot be empty");
        }

        // Validate address
        Address selectedAddress = storeAddressComboBox.getValue();
        if (selectedAddress == null) {
            errors.add("Please select an address");
        }

        // Validate items
        ObservableList<Item> selectedItems = storeItemsListView.getSelectionModel().getSelectedItems();
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
