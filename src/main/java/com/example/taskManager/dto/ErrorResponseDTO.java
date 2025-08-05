package com.example.taskManager.dto;

import org.springframework.http.HttpStatus;

//Record - сущность, добавленная в java 16, имеет встроенные getter и setter
public record ErrorResponseDTO(String message, HttpStatus status) {}
