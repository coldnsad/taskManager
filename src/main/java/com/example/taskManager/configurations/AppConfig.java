package com.example.taskManager.configurations;

import org.springframework.context.annotation.Bean;

import java.time.Clock;

@org.springframework.context.annotation.Configuration
public class AppConfig {
    @Bean
    public Clock getSystemClock(){
        return Clock.systemDefaultZone();
    }
}
