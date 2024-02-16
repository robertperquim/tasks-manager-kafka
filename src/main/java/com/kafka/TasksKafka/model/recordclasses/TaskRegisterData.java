package com.kafka.TasksKafka.model.recordclasses;


import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TaskRegisterData(
        @NotBlank
        String title,
        @NotBlank
        String description,
        @NotNull
        @Max(10)
        @Min(0)
        int priority
        ) {
}
