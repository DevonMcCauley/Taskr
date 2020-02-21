package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import static utilities.Database.submitQuery;

public class AddTaskController {

    //<editor-fold desc="FXML Declarations">
    @FXML
    private TextField txtTaskName;

    @FXML
    private TextField txtTaskDescription;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;
    //</editor-fold>

    //<editor-fold desc="Sounds">
    String backSound = getClass().getResource("/sounds/back.wav").toExternalForm();
    String addedSound = getClass().getResource("/sounds/taskAdded.wav").toExternalForm();

    Media back = new Media(backSound);
    MediaPlayer backClicked = new MediaPlayer(back);

    Media added = new Media(addedSound);
    MediaPlayer taskAdded = new MediaPlayer(added);
    //</editor-fold>

    //Used to change Scene
    String screen = "/view/task_screen.fxml";
    String title = "Taskr";


    @FXML
    void Cancel(ActionEvent event) {
        backClicked.seek(Duration.ZERO);
        backClicked.play();
        Taskr.Main.changeScene(screen, title);
    }

    @FXML
    void SubmitTask(ActionEvent event) {
        String taskDescription = txtTaskDescription.getText();
        String sqlStatement = "INSERT INTO Tasks (task_description) values ('" + taskDescription +"');";
        submitQuery(sqlStatement);
        taskAdded.seek(Duration.ZERO);
        taskAdded.play();

        Taskr.Main.changeScene(screen, title);
    }


}
