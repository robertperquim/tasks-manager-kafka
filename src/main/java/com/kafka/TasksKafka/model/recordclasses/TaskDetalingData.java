package com.kafka.TasksKafka.model.recordclasses;

import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.TaskState;

public record TaskDetalingData(String id, String title, String description, int priority) {

    public TaskDetalingData (Task task){
        this(task.getId(), task.getTitle(), task.getDescription(), task.getPriority());
    }


    public static Task toTask(String id, String title, String description, int priority) {
        return new Task(id, title, description, priority );
    }


}
