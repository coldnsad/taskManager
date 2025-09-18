package com.example.taskManager.services;

import com.example.taskManager.dto.RegistrationRequestDTO;

public interface UserService {
    void createUser(RegistrationRequestDTO request);
}
