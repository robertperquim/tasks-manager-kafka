package com.kafka.TasksKafka.exception;


import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;


@Data
@Builder

public class ErrorResponse {

    private int status;

    private String menssage;

    public static ErrorResponse internalError(RuntimeException ex) {
        return ErrorResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .menssage(ex.getMessage())
                .build();
    }

}
