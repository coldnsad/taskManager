package com.example.taskManager.services;

import com.example.taskManager.dto.ListTaskFilterDTO;
import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.dto.TaskFilteredDTO;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);

    TaskDTO getTaskById(Long id);

    TaskFilteredDTO getAllTasks(ListTaskFilterDTO filters, Pageable pageable);

    TaskDTO updateTask(Long id, TaskDTO updatedTask);

    void deleteTaskById(Long id);
}
