package com.example.taskManager.services;

import com.example.taskManager.exceptions.TaskNotFoundException;
import com.example.taskManager.models.Task;
import com.example.taskManager.repositories.TaskRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Clock;
import java.util.List;

@Service
public class TaskService {
    private final TaskRepository taskRepository;
    private Clock clock;

    public TaskService(Clock clock, TaskRepository taskRepository) {
        this.clock = clock;
        this.taskRepository = taskRepository;
    }

    //@Transactional используется для безопасного выполнения операции изменения данных(атомарность)
    @Transactional
    public Task createTask(Task task){
       return taskRepository.save(task);
    }

    public Task getTaskById(Long id){
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException(id));
    }

    public List<Task> getAllTasks(){
        return taskRepository.findAll();
    }

    @Transactional
    public Task updateTask(Long id, Task updatedTask){
        Task existingTask = getTaskById(id);
        existingTask.setTitle(updatedTask.getTitle());
        existingTask.setDescription(updatedTask.getDescription());
        existingTask.setCompleted(updatedTask.getCompleted());
        return taskRepository.save(existingTask);
    }

    @Transactional
    public void deleteTaskById(Long id){
        Task taskForDelete = getTaskById(id);
        taskRepository.delete(taskForDelete);
    }

    public List<Task> getTasksByCompletionStatus(boolean completed){
        return taskRepository.findByCompleted(completed);
    }

    public List<Task> getTasksByTitleKeyword(String keyword){
        return taskRepository.findByTitleKeyword(keyword);
    }

    //Вызов метода происходит после полной инициализации бина
    @PostConstruct
    public void init() {
        //System.out.println("clock.getZone(): " + clock.getZone());
    }
}
