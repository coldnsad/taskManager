package com.example.taskManager.services;

import com.example.taskManager.dto.RegistrationRequestDTO;
import com.example.taskManager.models.Role;
import com.example.taskManager.models.User;
import com.example.taskManager.repositories.RoleRepository;
import com.example.taskManager.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;

    @Override
    public void createUser(RegistrationRequestDTO request) {
        User newUser = new User();
        newUser.setName(request.getUserName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));

        if (!Objects.isNull(request.getRoleName())) {
            Role role = roleRepository.findByName(request.getRoleName())
                    .orElseThrow(() -> new RuntimeException("Role not found"));
            newUser.getRoles().add(role);
        }

        userRepository.save(newUser);
    }

}
