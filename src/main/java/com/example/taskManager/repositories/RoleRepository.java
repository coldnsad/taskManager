package com.example.taskManager.repositories;

import com.example.taskManager.models.Role;
import com.example.taskManager.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    Optional<Role> findByName(@NotBlank String name);
}
