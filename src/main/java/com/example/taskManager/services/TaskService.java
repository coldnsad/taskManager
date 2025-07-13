package com.example.taskManager.services;

import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.time.Clock;

@Service
public class TaskService {
    private Clock clock;
    public TaskService(Clock clock) {
        this.clock = clock;
    }

    @PostConstruct
    public void init() {
        System.out.println("clock.getZone(): " + clock.getZone());
    }
}
