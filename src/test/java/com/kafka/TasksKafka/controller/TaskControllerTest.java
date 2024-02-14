package com.kafka.TasksKafka.controller;

import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.service.TaskService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TaskControllerTest {

    @InjectMocks
    private TaskController taskController;

    @Mock
    private TaskService taskService;

    @Test
    public void controller_mustReturnOk_whenSaveSuccessfully() {
        // config WebTestClient
        WebTestClient client = WebTestClient.bindToController(taskController).build();


        // Mock da serivice para retornmo esperado
        when(taskService.createTask(any())).thenReturn(Mono.just(new TaskDetalingData(new Task())));

        // Teste da chamada ao endpoint
        client.post()
                .uri("/task/create")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(new Task())
                .exchange()
                .expectStatus().isOk()
                .expectBody(TaskDetalingData.class);
    }

    @Test
    public void controller_mustReturnNoContent_whenDaleteSuccessfully() {

        String taskId = "any-id";
        WebTestClient client = WebTestClient.bindToController(taskController).build();

        when(taskService.deleteTask(any())).thenReturn(Mono.empty());

        client.delete()
                .uri("/task/delete/{id}", taskId)
                .exchange()
                .expectStatus()
                .isNoContent();
    }


}

