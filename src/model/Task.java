package model;

import javafx.beans.value.ObservableValue;

public class Task {
    private ObservableValue<String> task_id;
    private ObservableValue<String> task_description;

    public Task(ObservableValue<String> task_id, ObservableValue<String> task_description) {
        this.task_id = task_id;
        this.task_description = task_description;
    }

    //Getters
    public ObservableValue<String> getTask_id() {
        return task_id;
    }
    public ObservableValue<String> getTask_description() {
        return task_description;
    }

}
