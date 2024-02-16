package com.kafka.TasksKafka.service;

import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.repository.TaskCustomRepository;
import com.kafka.TasksKafka.repository.TaskRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;



@Service
@AllArgsConstructor
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    private final TaskCustomRepository taskCustomRepository;

    public Mono<Page<Task>> findPagineted(Task task, Integer pageNumber, Integer pageSize) {
        return taskCustomRepository.findPagineted(task, pageNumber, pageSize);
    }

    public Mono<TaskDetalingData> createTask(TaskRegisterData data) {
        Task newTask = new Task(data);
        return  taskRepository.save(newTask)
                .doOnNext(it -> LOGGER.info("Saved task with title {}", data.title()))
                .map(task -> new TaskDetalingData(newTask))
                .doOnError(error -> LOGGER.error("Error during save task. Title {} ", data.title(), error));
    }

    public Mono<Void> deleteTask(String id) {
        return  taskRepository.deleteById(id);
    }


}
