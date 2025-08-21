package com.example.taskManager.services;

import com.example.taskManager.dto.CustomerDTO;
import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.exceptions.CustomerNotFoundException;
import com.example.taskManager.exceptions.TaskNotFoundException;
import com.example.taskManager.feign.CustomerProfileClient;
import com.example.taskManager.mappers.TaskMapper;
import com.example.taskManager.models.Task;
import com.example.taskManager.repositories.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final CustomerProfileClient customerProfileClient;

    public TaskService(Clock clock,
                       TaskRepository taskRepository,
                       TaskMapper taskMapper,
                       CustomerProfileClient customerProfileClient) {
        this.taskRepository = taskRepository;
        this.taskMapper = taskMapper;
        this.customerProfileClient = customerProfileClient;
    }

    //@Transactional используется для безопасного выполнения операции изменения данных(атомарность)
    //В случае ошибки - бд вернется в состояние до
    @Transactional
    public TaskDTO createTask(TaskDTO taskDTO){
        Task task = taskMapper.taskDTOToTask(taskDTO);
        Task savedTask = taskRepository.save(task);
        TaskDTO savedTaskDTO = taskMapper.taskToTaskDTO(savedTask);
        savedTaskDTO.setId(savedTask.getId());
       return savedTaskDTO;
    }

    public TaskDTO getTaskById(Long id){
        TaskDTO taskDTO;
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        if(task.getCustomerId() != null) {
            ResponseEntity<CustomerDTO> response = customerProfileClient.getCustomerById(task.getCustomerId());
            if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
                CustomerDTO customerDTO = response.getBody();
                taskDTO = taskMapper.toDTOWithCustomer(task, customerDTO);
            } else {
                taskDTO = taskMapper.taskToTaskDTO(task);
            }
        }else{
            taskDTO = taskMapper.taskToTaskDTO(task);
        }
        taskDTO.setId(task.getId());
        return taskDTO;
    }

    public List<TaskDTO> getAllTasks(){
        return taskMapper.tasksToTaskDTOs(taskRepository.findAll());
    }

    @Transactional
    public TaskDTO updateTask(Long id, TaskDTO updatedTask){
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        Task savedTask = taskRepository.save(existingTask);
        return taskMapper.taskToTaskDTO(savedTask);
    }

    @Transactional
    public void deleteTaskById(Long id){
        Task taskForDelete = taskMapper.taskDTOToTask(getTaskById(id));
        taskRepository.delete(taskForDelete);
    }

    public List<TaskDTO> getTasksByCompletionStatus(boolean completed){
        List<Task> tasks = taskRepository.findByCompleted(completed);
        return taskMapper.tasksToTaskDTOs(tasks);
    }

    public List<TaskDTO> getTasksByTitleKeyword(String keyword){
        List<Task> tasks = taskRepository.findByTitleKeyword(keyword);
        return taskMapper.tasksToTaskDTOs(tasks);
    }

    //Вызов метода происходит после полной инициализации бина
    @PostConstruct
    public void init() {
    }
}
