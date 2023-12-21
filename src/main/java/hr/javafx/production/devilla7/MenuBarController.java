package hr.javafx.production.devilla7;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

import java.io.IOException;

public class MenuBarController {
    public void showItemSearchScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "itemSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showCategorySearchScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "categorySearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showFactorySearchScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "factorySearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showStoreSearchScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "storeSearch.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showNewCategoryScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "addNewCategoryScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showNewItemScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "addNewItemScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showNewFactoryScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "addNewFactoryScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showNewStoreScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "addNewStoreScreen.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }

    public void showItemHistoryScreen() {
        FXMLLoader fxmlLoader =
                new FXMLLoader(HelloApplication.class.getResource(
                        "itemHistory.fxml"));
        Scene scene = null;
        try {
            scene = new Scene(fxmlLoader.load(), 680, 480);
        } catch (IOException e) {
            e.printStackTrace();
        }

        HelloApplication.getMainStage().setScene(scene);
        HelloApplication.getMainStage().show();
    }
}
