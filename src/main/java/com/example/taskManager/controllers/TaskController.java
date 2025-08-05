package com.example.taskManager.controllers;

import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.services.TaskService;
import jakarta.annotation.PostConstruct;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
public class TaskController {

    private final TaskService taskService;
    private String appName;

    public TaskController(TaskService taskService, @Value("${app.name}") String appName) {
        this.taskService = taskService;
        this.appName = appName;
    }

    @PostMapping("/api/tasks")
    public ResponseEntity<TaskDTO> addTask(@Valid @RequestBody TaskDTO taskDTO) {
        TaskDTO newTaskDTO = taskService.createTask(taskDTO);
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newTaskDTO.getId())
                .toUri();
        return ResponseEntity.created(location).body(newTaskDTO);
    }

    @GetMapping("/api/tasks")
    @ResponseStatus(HttpStatus.OK)
    public List<TaskDTO> getAllTasks() {
        return taskService.getAllTasks();
    }

    @GetMapping("/api/tasks/{id}")
    public ResponseEntity<TaskDTO> getTaskById(@PathVariable @Min(1L) Long id) {
        return ResponseEntity.ok(taskService.getTaskById(id));
    }

    @PostMapping("/api/tasks/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable @Min(1L) Long id, @Valid @RequestBody TaskDTO taskDTO) {
        return ResponseEntity.ok(taskService.updateTask(id, taskDTO));
    }

    @DeleteMapping("/api/tasks/{id}")
    public void deleteTaskById(@PathVariable @Min(1L) Long id) {
        taskService.deleteTaskById(id);
    }

    @GetMapping("/api/tasks/filter")
    public ResponseEntity<List<TaskDTO>> getAllTasksByStatus(@RequestParam Boolean completed) {
        if(completed != null){
            List<TaskDTO> taskDTOs = taskService.getTasksByCompletionStatus(completed);
            return ResponseEntity.ok(taskDTOs);
        }
        return ResponseEntity.ok(taskService.getAllTasks());
    }

    @GetMapping("/api/tasks/search")
    public ResponseEntity<List<TaskDTO>> getAllTasksByKeyword(@RequestParam String keyword) {
        List<TaskDTO> taskDTOs = taskService.getTasksByTitleKeyword(keyword);
        return ResponseEntity.ok(taskDTOs);
    }

    @PostConstruct
    public void init() {
        //System.out.println("appName: " + appName);
    }
}
