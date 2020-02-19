package controller;

import Taskr.Main;
import com.sun.corba.se.impl.orbutil.graph.Graph;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableArray;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import model.Task;
import utilities.Database;

import javax.xml.crypto.Data;
import java.net.URL;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.Optional;
import java.util.ResourceBundle;

import static utilities.Database.submitQuery;


public class TaskScreenController implements Initializable {
    @FXML
    private Label lblTitle;

    @FXML
    private Button btnAddTask;

    @FXML
    private TableView<Task> tblTasks;

    @FXML
    private TableColumn<Task, String> colTaskName;

    @FXML
    private TableColumn<Task, String> colTaskDescription;


    @FXML
    void AddTask(ActionEvent event) {
        try {
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

        if (task == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Invalid Selection");
            alert.setHeaderText("Invalid Selection");
            alert.setContentText("You must select a task to delete, first!");
            alert.initOwner(Main.getStage());
            alert.showAndWait();

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
                } catch (Exception e) {
                    System.out.println("Error: " + e.getMessage());
                }
            } else {
            }
        }
    }

    private void callDatabase() {
        ObservableList<Task> taskList = FXCollections.observableArrayList();

        colTaskName.setCellValueFactory(cellData -> {
            return cellData.getValue().getTask_name();
        });

        colTaskDescription.setCellValueFactory(cellData -> {
            return cellData.getValue().getTask_description();
        });


        ResultSet result = Database.selectTasksQuery();
        try {
            while (result.next()) {
                String task_id = result.getString("task_id");
                String task_name = result.getString("task_name");
                String task_description = result.getString("task_description");

                Task task = new Task(new ReadOnlyStringWrapper(task_id),
                        new ReadOnlyStringWrapper(task_name),
                        new ReadOnlyStringWrapper(task_description));
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
    }
}
