package com.example.taskManager.exceptions;

import com.example.taskManager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//Класс для глобальной обработки исключений в проекте
@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)

    //Производит отправку статуса 404 в случае, если происходит исключение TaskNotFoundException
    public ErrorResponseDTO handleTaskNotFound(TaskNotFoundException ex) {
        return new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND);
    }
}
