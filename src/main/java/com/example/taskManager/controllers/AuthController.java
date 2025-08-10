package com.example.taskManager.controllers;

import com.example.taskManager.dto.RegistrationRequestDTO;
import com.example.taskManager.models.Role;
import com.example.taskManager.models.User;
import com.example.taskManager.repositories.RoleRepository;
import com.example.taskManager.repositories.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    public AuthController(PasswordEncoder passwordEncoder,
                          RoleRepository roleRepository,
                          UserRepository userRepository) {
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
        this.userRepository = userRepository;
    }

    @PostMapping("/signup")
    public ResponseEntity<String> addTask(@RequestBody RegistrationRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getUserName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if(!Objects.isNull(request.getRoleName())){
            Role role = roleRepository.findByName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.getRoles().add(role);
        }

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered");
    }
}
