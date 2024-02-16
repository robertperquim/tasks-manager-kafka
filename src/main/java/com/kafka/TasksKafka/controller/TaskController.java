package com.kafka.TasksKafka.controller;


import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.TaskState;
import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.service.TaskService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("task")
public class TaskController {

    private static final Logger LOGGER = LoggerFactory.getLogger(TaskController.class);

    @Autowired(required = false)
    private TaskService taskService;





    @GetMapping("list")
    public ResponseEntity<Mono<Page<TaskDetalingData>>> getTasks(@RequestParam(required = false) String id,
                                                                 @RequestParam(required = false) String title,
                                                                 @RequestParam(required = false) String description,
                                                                 @RequestParam(required = false, defaultValue = "0") int priority,
                                                                 @RequestParam(required = false) TaskState taskState,
                                                                 @RequestParam(value = "pageNumber", defaultValue = "0") Integer pageNumber,
                                                                 @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
                                                                 ) {




        return ResponseEntity.ok(taskService.findPagineted(TaskDetalingData.toTask(id, title, description, priority), pageNumber, pageSize)
                .map(it -> it.map(TaskDetalingData::new)));


    }

    @PostMapping("create")
    public Mono<ResponseEntity<TaskDetalingData>> createTask(@RequestBody @Valid TaskRegisterData data) {
        return taskService.createTask(data)
                .doOnNext(taskDetalingData -> LOGGER.info("Saved task with id {}", taskDetalingData.id()))
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<Object>> deleteTask(@PathVariable String id) {
        return taskService.deleteTask(id)
                .then(Mono.just(ResponseEntity.noContent().build())
                        .doOnNext(it -> LOGGER.info("Deleting task with id {}", id)));
    }



}
