package com.kafka.TasksKafka.service;


import com.kafka.TasksKafka.model.Task;
import com.kafka.TasksKafka.model.recordclasses.TaskDetalingData;
import com.kafka.TasksKafka.model.recordclasses.TaskRegisterData;
import com.kafka.TasksKafka.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import java.util.List;



@Service
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public Mono<Page<TaskDetalingData>> listTasks(Pageable pageable) {
        return Mono.fromCallable(()->{
            Page<Task> pageTasks =  taskRepository.findAll(pageable); // cria uma pagina de tasks
            List<TaskDetalingData> taskDetalingData = taskRepository.findAll(pageable).getContent()
                    .stream()
                    .map(TaskDetalingData::new)
                    .toList(); // crio a lista de dtos com os dados das tasks
            return new PageImpl<>(taskDetalingData, pageable, pageTasks.getTotalElements()); //crio uma page de dtos
        });
    }

    public Mono<TaskDetalingData> createTask(TaskRegisterData data) {
        Task newTask = new Task(data);
        return Mono.fromCallable(() -> taskRepository.save(newTask))
                .map(task -> new TaskDetalingData(newTask));
    }

    public Mono<?> deleteTask(String id) {
        return Mono.fromRunnable(() -> taskRepository.deleteById(id));
    }
}
