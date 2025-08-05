package com.example.taskManager.exceptions;

import com.example.taskManager.dto.ErrorResponseDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

//Класс для глобальной обработки исключений в проекте
@ControllerAdvice
public class GlobalExceptionHandler {

    //Производит отправку ответа 404(NOT_FOUND) в читаемом виде в случае,
    //если происходит исключение TaskNotFoundException
    @ExceptionHandler(TaskNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity<ErrorResponseDTO> handleTaskNotFound(TaskNotFoundException ex) {
        ErrorResponseDTO error = new ErrorResponseDTO(ex.getMessage(), HttpStatus.NOT_FOUND);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    //Производит отправку ответа 400(BAD_REQUEST) в читаемом виде в случае,
    //если происходит исключение MethodArgumentNotValidException
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ErrorResponseDTO> handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        FieldError fieldError =  ex.getBindingResult().getFieldErrors().getFirst();
        String message = fieldError.getField() +  " " + fieldError.getDefaultMessage();
        ErrorResponseDTO error = new ErrorResponseDTO(message, HttpStatus.BAD_REQUEST);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
