package com.kafka.TasksKafka.model.recordclasses;

import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.TaskState;

public record TaskDetalingData(String id, String title, String description, int priority, TaskState taskState) {

    public TaskDetalingData (Task task){
        this(task.getId(), task.getTitle(), task.getDescription(), task.getPriority(), task.getState());
    }
}
