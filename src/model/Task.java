package model;

import javafx.beans.value.ObservableValue;

import java.util.Date;

public class Task {
    private ObservableValue<String> task_id;
    private ObservableValue<String> task_description;
    private ObservableValue<String> task_date;

    public Task(ObservableValue<String> task_id, ObservableValue<String> task_description, ObservableValue<String> task_date) {
        this.task_id = task_id;
        this.task_description = task_description;
        this.task_date = task_date;

    }

    //Getters
    public ObservableValue<String> getTask_id() {
        return task_id;
    }
    public ObservableValue<String> getTask_description() {
        return task_description;
    }
    public ObservableValue<String> getTask_date() {
        return task_date;
    }

}
