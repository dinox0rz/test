package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.*;
import hr.javafx.production.devilla7.enumeration.City;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class StoreSearchController {
    @FXML
    private TextField storeNameTextField;
    @FXML
    private ComboBox<City> cityComboBox;
    @FXML
    private TextField itemNameTextField;
    @FXML
    private TableView<Store> storeTableView;
    @FXML
    private TableColumn<Store,String> storeNameColumn;
    @FXML
    private TableColumn<Store,String> storeWebAddressColumn;
    @FXML
    private TableColumn<Store,String> storeAddressColumn;
    @FXML
    private TableColumn<Store,String> storeItemsSetColumn;

    public void initialize(){
        List<City> cities = Arrays.asList(City.values());
        ObservableList<City> cityList = FXCollections.observableArrayList(cities);
        cityComboBox.setItems(cityList);
        storeNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        storeWebAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWebAddress()));
        storeAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress().toString()));
        storeItemsSetColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getItemsAsString(cellData.getValue().getItems())));
    }

    private String getItemsAsString(Set<Item> items) {
        return items.stream()
                .map(Item::getName)
                .collect(Collectors.joining(", "));
    }

    public void storeSearch(){
        //List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        //List<Item> itemList = FileUtils.getItemsFromFile(categoryList);
        List<Item> itemList = DatabaseUtils.getAllItemsFromDatabase(categoryList);
        //List<Address> addressList = FileUtils.getAddressesFromFile();
        List<Address> addressList = DatabaseUtils.getAddressesFromDatabase();
        //List<Store> storeList = FileUtils.getStoresFromFile(itemList,addressList);
        List<Store> storeList = DatabaseUtils.getStoresFromDatabase(categoryList);
        String storeName = storeNameTextField.getText();
        String itemName = itemNameTextField.getText();
        City selectedCity = cityComboBox.getValue();
        List<Store> filteredStoreList;

        if (selectedCity != null && selectedCity != City.ANY_CITY) {
            filteredStoreList = storeList.stream()
                    .filter(factory -> factory.getName().contains(storeName) &&
                            factory.getAddress().getCity().equals(selectedCity) &&
                            factory.getItems().stream().anyMatch(item -> item.getName().contains(itemName)))
                    .collect(Collectors.toList());
        } else {
            filteredStoreList = storeList.stream()
                    .filter(factory -> factory.getName().contains(storeName) &&
                            factory.getItems().stream().anyMatch(item -> item.getName().contains(itemName)))
                    .collect(Collectors.toList());
        }

        ObservableList observableFactoriesList = FXCollections.observableArrayList(filteredStoreList);
        storeTableView.setItems(observableFactoriesList);
    }
}
