package com.example.taskManager.services;

import com.example.taskManager.dto.CustomerDTO;
import com.example.taskManager.dto.ListTaskFilterDTO;
import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.dto.TaskFilteredDTO;
import com.example.taskManager.exceptions.TaskNotFoundException;
import com.example.taskManager.feign.CustomerProfileClient;
import com.example.taskManager.mappers.TaskMapper;
import com.example.taskManager.models.Task;
import com.example.taskManager.repositories.TaskRepository;
import com.example.taskManager.specification.TaskSpecification;
import jakarta.annotation.PostConstruct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CustomerProfileClient customerProfileClient;

    public TaskServiceImpl(TaskRepository taskRepository,
                           TaskMapper taskMapper,
                           CustomerProfileClient customerProfileClient) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.customerProfileClient = customerProfileClient;
    }

    //@Transactional используется для безопасного выполнения операции изменения данных(атомарность)
    //В случае ошибки - бд вернется в состояние до
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = taskMapper.taskDTOToTask(taskDTO);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = taskMapper.taskToTaskDTO(savedTask);
        savedTaskDTO.setId(savedTask.getId());
        return savedTaskDTO;
    }

    public TaskDTO getTaskById(Long id) {
        TaskDTO taskDTO;
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if (task.getCustomerId() != null) {
            ResponseEntity<CustomerDTO> response = customerProfileClient.getCustomerById(task.getCustomerId());
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                CustomerDTO customerDTO = response.getBody();
                taskDTO = taskMapper.toDTOWithCustomer(task, customerDTO);
            } else {
                taskDTO = taskMapper.taskToTaskDTO(task);
            }
        } else {
            taskDTO = taskMapper.taskToTaskDTO(task);
        }
        taskDTO.setId(task.getId());
        return taskDTO;
    }

    @Override
    public TaskFilteredDTO getAllTasks(ListTaskFilterDTO filters, Pageable pageable) {
        Specification<Task> taskSpecification = TaskSpecification.build(filters);
        Page<Task> pagedTask = taskRepository.findAll(taskSpecification, pageable);
        List<TaskDTO> taskDTOList = pagedTask.getContent().stream()
                .map(taskMapper::taskToTaskDTO)
                .toList();
        TaskFilteredDTO taskFilteredDTO = new TaskFilteredDTO(
                taskDTOList,
                pagedTask.getNumber(),
                pagedTask.getNumberOfElements(),
                pagedTask.getTotalPages(),
                pagedTask.getTotalElements()
        );
        return taskFilteredDTO;
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO updatedTask) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        Task savedTask = taskRepository.save(existingTask);
        return taskMapper.taskToTaskDTO(savedTask);
    }

    @Transactional
    public void deleteTaskById(Long id) {
        Task taskForDelete = taskMapper.taskDTOToTask(getTaskById(id));
        taskRepository.delete(taskForDelete);
    }

    //Вызов метода происходит после полной инициализации бина
    @PostConstruct
    public void init() {
    }
}
