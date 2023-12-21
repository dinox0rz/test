package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Address;
import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.domain.Factory;
import hr.javafx.production.devilla7.domain.Item;
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
import javafx.util.StringConverter;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class FactorySearchController {
    @FXML
    private TextField factoryNameTextField;
    @FXML
    private ComboBox<City> cityComboBox;
    @FXML
    private TextField itemNameTextField;
    @FXML
    private TableView<Factory> factoryTableView;
    @FXML
    private TableColumn<Factory,String> factoryNameColumn;
    @FXML
    private TableColumn<Factory,String> factoryAddressColumn;
    @FXML
    private TableColumn<Factory,String> factoryItemsSetColumn;

    public void initialize(){
        List<City> cities = Arrays.asList(City.values());
        ObservableList<City> cityList = FXCollections.observableArrayList(cities);
        cityComboBox.setItems(cityList);
        factoryNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        factoryAddressColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getAddress().toString()));
        factoryItemsSetColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(getItemsAsString(cellData.getValue().getItems())));
    }
    private String getItemsAsString(Set<Item> items) {
        return items.stream()
                .map(Item::getName)
                .collect(Collectors.joining(", "));
    }

    public void factorySearch(){
        //List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        //List<Item> itemList = FileUtils.getItemsFromFile(categoryList);
        List<Item> itemList = DatabaseUtils.getAllItemsFromDatabase(categoryList);
        //List<Address> addressList = FileUtils.getAddressesFromFile();
        List<Address> addressList = DatabaseUtils.getAddressesFromDatabase();
        List<Factory> factoryList = DatabaseUtils.getFactoriesFromDatabase(addressList,categoryList);
        String factoryName = factoryNameTextField.getText();
        String itemName = itemNameTextField.getText();
        City selectedCity = cityComboBox.getValue();
        List<Factory> filteredFactoriesList;

        if (selectedCity != null && selectedCity != City.ANY_CITY) {
            filteredFactoriesList = factoryList.stream()
                    .filter(factory -> factory.getName().contains(factoryName) &&
                            factory.getAddress().getCity().equals(selectedCity) &&
                            factory.getItems().stream().anyMatch(item -> item.getName().contains(itemName)))
                    .collect(Collectors.toList());
        } else {
            filteredFactoriesList = factoryList.stream()
                    .filter(factory -> factory.getName().contains(factoryName) &&
                            factory.getItems().stream().anyMatch(item -> item.getName().contains(itemName)))
                    .collect(Collectors.toList());
        }

        ObservableList observableFactoriesList = FXCollections.observableArrayList(filteredFactoriesList);
        factoryTableView.setItems(observableFactoriesList);
    }
    }
