package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.domain.Item;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.stream.Collectors;

public class ItemSearchController {
    @FXML
    private TextField itemNameTextField;
    @FXML
    private ComboBox<Category> itemCategoryComboBox;
    @FXML
    private TableView<Item> itemTableView;
    @FXML
    private TableColumn<Item, String> itemNameColumn;
    @FXML
    private TableColumn<Item, String> categoryColumn;
    @FXML
    private TableColumn<Item, String> widthColumn;
    @FXML
    private TableColumn<Item, String> heightColumn;
    @FXML
    private TableColumn<Item, String> lengthColumn;
    @FXML
    private TableColumn<Item, String> productionCostColumn;
    @FXML
    private TableColumn<Item, String> sellingPriceColumn;

    public void initialize(){

        //List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        Category noCategory = new Category(-1L, "No category", "No description");
        categoryList.add(noCategory);
        itemCategoryComboBox.setItems(FXCollections.observableArrayList(categoryList));
        itemCategoryComboBox.setValue(noCategory);

        itemNameColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));

        categoryColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getCategory().getName()));

        widthColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getWidth().toString()));

        heightColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getHeight().toString()));

        lengthColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getLength().toString()));

        productionCostColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getProductionCost().toString()));

        sellingPriceColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getSellingPrice().toString()));
    }
    public void itemSearch(){
        //List<Category> categoryList = FileUtils.getCategoriesFromFile();
        //List<Item> itemList = FileUtils.getItemsFromFile(categoryList);
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        List<Item> itemList = DatabaseUtils.getAllItemsFromDatabase(categoryList);
        String itemName = itemNameTextField.getText();
        Category selectedCategory = itemCategoryComboBox.getValue();
        List<Item> filteredItemsList;

        if (selectedCategory != null && selectedCategory.getId() != -1L) {
            filteredItemsList = itemList.stream()
                    .filter(item -> item.getName().contains(itemName) && item.getCategory().equals(selectedCategory))
                    .collect(Collectors.toList());
        } else {
            filteredItemsList = itemList.stream()
                    .filter(item -> item.getName().contains(itemName))
                    .collect(Collectors.toList());
        }
        ObservableList observableItemsList = FXCollections.observableArrayList(filteredItemsList);
        itemTableView.setItems(observableItemsList);
    }


}
