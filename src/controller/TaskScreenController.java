package controller;

import Taskr.Main;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;
import model.Task;
import utilities.Database;

import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.Optional;
import java.util.ResourceBundle;

import static utilities.Database.submitQuery;


public class TaskScreenController implements Initializable {

    //<editor-fold desc="FXML Declarations">
    @FXML
    private AnchorPane root;

    @FXML
    private Label lblTitle;

    @FXML
    private Button btnAddTask;

    @FXML
    private TableView<Task> tblTasks;

    @FXML
    private TableColumn<Task, String> colTaskDescription;

    @FXML
    private TableColumn<Task, String> colDueDate;
    //</editor-fold>

    //<editor-fold desc="Sounds">
    String clickSound = getClass().getResource("/sounds/click.mp3").toExternalForm();
    String deleteSound = getClass().getResource("/sounds/deleted.mp3").toExternalForm();

    Media click = new Media(clickSound);
    MediaPlayer buttonClicked = new MediaPlayer(click);

    Media delete = new Media(deleteSound);
    MediaPlayer deleteClicked = new MediaPlayer(delete);
    //</editor-fold>


    @FXML
    void AddTask(ActionEvent event) {
        try {
            buttonClicked.seek(Duration.ZERO);
            buttonClicked.play();
            Thread.sleep(400);
            String screen = "/view/add_task.fxml";
            String title = "Add Task";
            Taskr.Main.changeScene(screen, title);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    void DeleteTask(ActionEvent event) {
        String sqlStatement;

        Task task = tblTasks.getSelectionModel().getSelectedItem();

        //Checks that user selects a task to delete
        if (task == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("You must select a task to delete, first!");
            alert.initOwner(Main.getStage());
            alert.showAndWait();

            //Deletes the task from the database
        } else {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirm Delete");
            alert.setHeaderText("Confirm Delete");
            alert.setContentText("Are you ok with deleting this task?");
            alert.initOwner(Main.getStage());
            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK) {
                try {
                    sqlStatement = "DELETE FROM Tasks WHERE task_id = " + task.getTask_id().getValue() + ";";
                    submitQuery(sqlStatement);
                    callDatabase();
                    deleteClicked.seek(Duration.ZERO);
                    deleteClicked.play();
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
            }
        }
    }


    private void callDatabase() {
        ObservableList<Task> taskList = FXCollections.observableArrayList();

        colTaskDescription.setCellValueFactory(cellData -> {
            return cellData.getValue().getTask_description();
        });
        colDueDate.setCellValueFactory(cellData -> {
            return cellData.getValue().getTask_date();
        });

        ResultSet result = Database.selectTasksQuery();
        try {
            while (result.next()) {
                String task_id = result.getString("task_id");
                String task_description = result.getString("task_description");
                String task_date = result.getString("task_date");
                if (task_date.equals("null")) {
                    task_date = "n/a";
                }
                Task task = new Task(new ReadOnlyStringWrapper(task_id),
                        new ReadOnlyStringWrapper(task_description),
                        new ReadOnlyStringWrapper(task_date));
                taskList.add(task);
            }
            tblTasks.setItems(taskList);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        callDatabase();
        ResultSet result = Database.checkDates();

        try {
            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.WARNING, "You have a task date that has passed!");
                alert.showAndWait();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
