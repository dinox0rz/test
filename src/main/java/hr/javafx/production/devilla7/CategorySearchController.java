package hr.javafx.production.devilla7;

import hr.javafx.production.devilla7.domain.Category;
import hr.javafx.production.devilla7.utils.DatabaseUtils;
import hr.javafx.production.devilla7.utils.FileUtils;
import javafx.beans.Observable;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.util.List;

public class CategorySearchController {
    @FXML
    private TableView<Category> categoryTableView;
    @FXML
    private TableColumn<Category,String> categoryNameTableColumn;
    @FXML
    private TableColumn<Category,String> categoryDescriptionTableColumn;

    public void initialize(){
        //List<Category> categoryList = FileUtils.getCategoriesFromFile();
        List<Category> categoryList = DatabaseUtils.getAllCategoriesFromDatabase();
        categoryNameTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getName()));
        categoryDescriptionTableColumn.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getDescription()));
        ObservableList observableCategoryList = FXCollections.observableArrayList(categoryList);
        categoryTableView.setItems(observableCategoryList);
    }

}
