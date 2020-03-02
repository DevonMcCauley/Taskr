package utilities;

import Taskr.Main;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import utilities.Database;

import java.io.IOException;

public class ScreenManager  {

    static Stage stage;
    public static Image ic = new Image(("/images/logo_icon.png"));

    //getStage method for setting titles of windows & changing scenes
    public static Stage getStage() {
        return stage;
    }

    //Method to change the scene
    public static void changeScene(String screen, String title) {
        Parent main = null;
        try {
            main = FXMLLoader.load(Main.class.getResource(screen));
            Scene scene = new Scene(main);
            Stage stage = Main.getStage();
            stage.setScene(scene);
            stage.getIcons().add(ic);
            stage.setTitle(title);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
