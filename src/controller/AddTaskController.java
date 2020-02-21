package controller;

import com.sun.org.apache.xml.internal.security.Init;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utilities.Database.submitQuery;

public class AddTaskController implements Initializable {

    //<editor-fold desc="FXML Declarations">
    @FXML
    private TextField txtTaskName;

    @FXML
    private TextField txtTaskDescription;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;

    @FXML
    private DatePicker calTaskDate;
    @FXML
    private CheckBox chkNoEndDate;
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
        String sqlStatement;
        String taskDescription;

        taskDescription = txtTaskDescription.getText();
        LocalDate taskDate = calTaskDate.getValue();
        sqlStatement = "INSERT INTO Tasks (task_description, task_date) values ('" + taskDescription + "', '" + taskDate + "');";
        submitQuery(sqlStatement);
        taskAdded.seek(Duration.ZERO);
        taskAdded.play();
        Taskr.Main.changeScene(screen, title);

    }

    private boolean checkDescription() {

        boolean bool = true;
        if (txtTaskDescription.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "You must add a task description");
            alert.showAndWait();
            bool = false;
        }
        return bool;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        chkNoEndDate.selectedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                if (chkNoEndDate.isSelected()) {
                    calTaskDate.setDisable(true);
                } else {
                    calTaskDate.setDisable(false);
                }
            }
        });
    }
}
