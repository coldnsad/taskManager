package com.example.taskManager.controllers;

import com.example.taskManager.services.TaskService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;

@Controller
public class TaskController {

    private final TaskService taskService;

    private String appName;

    public TaskController(TaskService taskService, @Value("${app.name}") String appName) {
        this.taskService = taskService;
        this.appName = appName;
    }

    @PostConstruct
    public void init() {
        //System.out.println("appName: " + appName);
    }
}
