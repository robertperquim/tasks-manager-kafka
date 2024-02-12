package com.kafka.TasksKafka.model;


import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Task {
    @Id
    private String id;
    private String title;
    private String description;
    private int priority;
    private TaskState state;

    public Task(TaskRegisterData data) {
        this.title = data.title();
        this.description = data.description();
        this.priority = data.priority();
        //this.state = data.taskState();
    }

}
