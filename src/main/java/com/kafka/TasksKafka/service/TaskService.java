package com.kafka.TasksKafka.service;

import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.repository.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TaskService {
    private static final Logger LOGGER = LoggerFactory.getLogger(TaskService.class);

    @Autowired
    private TaskRepository taskRepository;

    public Mono<Page<TaskDetalingData>> listTasks(Pageable pageable) {
        return Mono.fromSupplier(() -> {
            Page<Task> pageTasks = taskRepository.findAll(pageable);
            List<TaskDetalingData> taskDetalingData = pageTasks.getContent()
                    .stream()
                    .map(TaskDetalingData::new)
                    .collect(Collectors.toList());
            return new PageImpl<>(taskDetalingData, pageable, pageTasks.getTotalElements());
        });
    }

    public Mono<TaskDetalingData> createTask(TaskRegisterData data) {
        Task newTask = new Task(data);
        return Mono.fromCallable(() -> taskRepository.save(newTask))
                .doOnNext(it -> LOGGER.info("Saved task with title {}", it.getTitle()))
                .map(task -> new TaskDetalingData(newTask))
                .doOnError(error -> LOGGER.error("Error during save task. Title {} ", data.title(), error));
    }

    public Mono<Void> deleteTask(String id) {
        return Mono.fromRunnable(() -> taskRepository.deleteById(id));
    }

}
