package com.example.taskManager.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TaskDTO {
    private String title;
    private String description;
    private Boolean completed;

    public TaskDTO() {}
}
