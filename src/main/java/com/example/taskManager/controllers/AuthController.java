package com.example.taskManager.controllers;

import com.example.taskManager.dto.LoginRequestDTO;
import com.example.taskManager.dto.RegistrationRequestDTO;
import com.example.taskManager.services.AuthenticationService;
import com.example.taskManager.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("/task-api/auth")
public class AuthController {

    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequestDTO request) {
        userService.createUser(request);
        return ResponseEntity.ok("User registered");
    }

    @GetMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginRequestDTO request) {
        return ResponseEntity.ok(authenticationService.signIn(request));
    }
}
