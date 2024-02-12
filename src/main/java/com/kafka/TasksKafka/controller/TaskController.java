package com.kafka.TasksKafka.controller;


import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.service.TaskService;
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

    @Autowired
    private TaskService taskService;

    @GetMapping("list")
    public Mono<ResponseEntity<Page<TaskDetalingData>>> getTasks(@PageableDefault(size = 2, sort = {"title"}) Pageable pageable) {
       return taskService.listTasks(pageable).map(ResponseEntity::ok);
    }

    @PostMapping("create")
    public Mono<ResponseEntity<TaskDetalingData>> createTask(@RequestBody TaskRegisterData data) {
        return taskService.createTask(data).map(ResponseEntity::ok);
    }

    @DeleteMapping("delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<ResponseEntity<?>> deleteTask(@RequestParam String id){
        return Mono.just(id)
                .flatMap(taskService::deleteTask).map(ResponseEntity::ok);
    }


}
