package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

import static utilities.Database.submitQuery;

public class AddTaskController {

    @FXML
    private TextField txtTaskName;

    @FXML
    private TextField txtTaskDescription;

    @FXML
    private Button btnSubmit;

    @FXML
    private Button btnCancel;



    String screen = "/view/task_screen.fxml";
    String title = "Taskr";


    @FXML
    void Cancel(ActionEvent event) {
        Taskr.Main.changeScene(screen, title);
    }

    @FXML
    void SubmitTask(ActionEvent event) {
        String taskName = txtTaskName.getText();
        String taskDescription = txtTaskDescription.getText();
        String sqlStatement = "INSERT INTO Tasks (task_name, task_description) values ('" + taskName + "', '" + taskDescription + "');";
        submitQuery(sqlStatement);

        Taskr.Main.changeScene(screen, title);
    }


}
