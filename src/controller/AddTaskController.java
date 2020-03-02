package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Callback;
import javafx.util.Duration;
import utilities.ScreenManager;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

import static utilities.Database.submitQuery;

public class AddTaskController implements Initializable {

    //<editor-fold desc="FXML Declarations">
    @FXML
    private TextField txtTaskDescription;

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


    //Returns user to the main screen (Task Screen)
    @FXML
    void Cancel(ActionEvent event) {
        backClicked.seek(Duration.ZERO);
        backClicked.play();
        ScreenManager.changeScene(screen, title);
    }

    //Submits Task entered by user to the SQLite database
    @FXML
    void SubmitTask(ActionEvent event) {
        String sqlStatement;
        String taskDescription;

        if (checkTaskDescription())
        {
            taskDescription = txtTaskDescription.getText();
            LocalDate taskDate = calTaskDate.getValue();
            sqlStatement = "INSERT INTO Tasks (task_description, task_date) values ('" + taskDescription + "', '" + taskDate + "');";
            submitQuery(sqlStatement);
            taskAdded.seek(Duration.ZERO);
            taskAdded.play();
            ScreenManager.changeScene(screen, title);
        }
    }

    boolean checkTaskDescription(){
        boolean isOccupied = true;
        if(txtTaskDescription.getText().isEmpty())
        {
        Alert alert = new Alert(Alert.AlertType.ERROR, "You need to enter a Task description!");
        alert.showAndWait();
        isOccupied = false;
        }
        return isOccupied;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        final Callback<DatePicker, DateCell> dayCellFactory = new Callback<DatePicker, DateCell>() {
            public DateCell call(final DatePicker datePicker) {
                return new DateCell() {
                    @Override public void updateItem(LocalDate day, boolean empty) {
                        super.updateItem(day, empty);

                        if (day.isBefore(LocalDate.now())) {
                            setStyle("-fx-background-color: #ff6f69;");
                            setDisable(true);
                        }
                        if(day.equals(LocalDate.now()) || day.isAfter(LocalDate.now()))
                        {
                            setStyle("-fx-background-color: #96ceb4;");
                        }
                    }
                };
            }
        };
        calTaskDate.setDayCellFactory(dayCellFactory);

        //Creates a listener on the chkNoEndDate checkbox & disables the calendar widget if checked
        chkNoEndDate.selectedProperty().addListener((observable, oldValue, newValue) -> {
            if (chkNoEndDate.isSelected()) {
                calTaskDate.setDisable(true);
            } else {
                calTaskDate.setDisable(false);
            }
        });
    }
}
