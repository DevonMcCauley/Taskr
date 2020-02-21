package Taskr;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import utilities.Database;

import java.io.IOException;

public class Main extends Application {

    static Stage stage;
    public static Image ic = new Image(("/images/logo_icon.png"));

    @Override
    public void start(Stage stage) {
        this.stage = stage;

        Parent main = null;
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/task_screen.fxml"));
            main = loader.load();
            Scene scene = new Scene(main);
            stage.setScene(scene);
            stage.getIcons().add(ic);
            stage.setTitle("Taskr");
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public static void main(String args[]) {
        Database.startConnection();
        if (!Database.checkDatabase()) {
            Database.createTasksTable();
        }
        launch(args);
        Database.closeConnection();
    }
}
