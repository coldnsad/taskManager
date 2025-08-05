package com.example.taskManager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private Long id;
    @NotBlank
    private String title;
    @Size(min = 1, max = 40)
    private String description;
    @NotNull
    private Boolean completed;

    public TaskDTO() {}
}
