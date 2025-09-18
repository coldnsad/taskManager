package com.example.taskManager.services;

import com.example.taskManager.dto.LoginRequestDTO;

public interface AuthenticationService {
    String signIn(LoginRequestDTO request);
}
