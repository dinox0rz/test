package hr.javafx.production.devilla7;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    public static Stage mainStage;
    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("firstScreen.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 640, 480);
        stage.setTitle("Production application");
        stage.setScene(scene);
        stage.show();
    }

    public static Stage getMainStage() {
        return mainStage;
    }

    public static void main(String[] args) {
        launch();
    }
}