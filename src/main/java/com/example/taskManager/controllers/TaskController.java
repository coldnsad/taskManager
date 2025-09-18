package com.example.taskManager.controllers;

import com.example.taskManager.dto.ListTaskFilterDTO;
import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.dto.TaskFilteredDTO;
import com.example.taskManager.services.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/task-api")
@AllArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @PostMapping("/tasks")
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO newTaskDTO = taskService.createTask(taskDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTaskDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(newTaskDTO);
    }

    /**
     *
     * @param pageable
     * При запросе для пагинации указывать следующие параметры запроса
     * page(default = 0) - номер страницы
     * size(default = 20) - количество элементов на странице
     * sort - поле и направление сортировки
     * Пример запроса:
     * GET /task-api/tasks?page=0&size=1&sort=completed,desc
     */
    @GetMapping("/tasks")
    @ResponseStatus(HttpStatus.OK)
    public TaskFilteredDTO getAllTasks(@ModelAttribute ListTaskFilterDTO filters, Pageable pageable) {
        return taskService.getAllTasks(filters, pageable);
    }

    @GetMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(1L) Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PutMapping("/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable @Min(1L) Long id, @Valid @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/tasks/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteTaskById(@PathVariable @Min(1L) Long id) {
        taskService.deleteTaskById(id);
    }

    @PostConstruct
    public void init() {
        //System.out.println("appName: " + appName);
    }
}
