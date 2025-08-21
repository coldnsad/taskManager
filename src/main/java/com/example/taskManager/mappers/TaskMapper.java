package com.example.taskManager.mappers;

import com.example.taskManager.dto.CustomerDTO;
import com.example.taskManager.dto.TaskDTO;
import com.example.taskManager.models.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

//Mapper нужен для удобного преобразования сущности jpa <-> dto
//В данном случае используется mapstruct
@Mapper(componentModel = "spring")
public interface TaskMapper {
    //@Mapping(target = "id", ignore = true)
    TaskDTO taskToTaskDTO(Task task);
    Task taskDTOToTask(TaskDTO taskDTO);
    List<TaskDTO> tasksToTaskDTOs(List<Task> tasks);
    List<Task> taskDTOsToTasks(List<TaskDTO> taskDTOs);

    // Маппинг Task + User -> TaskDTO с информацией о пользователе
    @Mapping(target = "customer", source = "customerDTO")
    TaskDTO toDTOWithCustomer(Task task, CustomerDTO customerDTO);

    // Маппинг Customer -> CustomerInfo (вложенный класс в TaskDTO)
    @Mapping(target = "userName", source = "userName")
    @Mapping(target = "email", source = "email")
    TaskDTO.CustomerInfo customerDTOToCustomerInfo(CustomerDTO customerDTO);
}
