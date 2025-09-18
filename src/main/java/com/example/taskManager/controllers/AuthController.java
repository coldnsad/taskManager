package com.example.taskManager.controllers;

import com.example.taskManager.dto.LoginRequestDTO;
import com.example.taskManager.dto.RegistrationRequestDTO;
import com.example.taskManager.jwt.JwtTokenProvider;
import com.example.taskManager.models.Role;
import com.example.taskManager.models.User;
import com.example.taskManager.repositories.RoleRepository;
import com.example.taskManager.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Objects;

@RestController
@AllArgsConstructor
@RequestMapping("/task-api/auth")
public class AuthController {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<String> signUp(@RequestBody RegistrationRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getUserName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (!Objects.isNull(request.getRoleName())) {
            Role role = roleRepository.findByName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.getRoles().add(role);
        }

        userRepository.save(newUser);
        return ResponseEntity.ok("User registered");
    }

    @GetMapping("/signin")
    public ResponseEntity<String> signIn(@RequestBody LoginRequestDTO request) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getUserName(), request.getPassword()));
        String token = jwtTokenProvider.generateToken(
                auth.getName(),
                new ArrayList<>(auth.getAuthorities()));

        return ResponseEntity.ok(token);
    }
}
