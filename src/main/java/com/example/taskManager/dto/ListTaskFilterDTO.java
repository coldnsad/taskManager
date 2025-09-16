package com.example.taskManager.dto;

public record ListTaskFilterDTO(
        String title,
        String description,
        Boolean completed
) {
}
