package com.example.taskManager.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    @Schema(hidden = true)
    private Long id;
    @NotBlank
    private String title;
    @Size(min = 1, max = 40)
    private String description;
    @NotNull
    private Boolean completed;
    private CustomerInfo customer;

    public TaskDTO() {}

    @Data
    @AllArgsConstructor
    public static class CustomerInfo{
        Long customerId;
        String userName;
        String email;
    }
}
