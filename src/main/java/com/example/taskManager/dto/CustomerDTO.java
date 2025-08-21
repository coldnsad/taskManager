package com.example.taskManager.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long customerId;
    private String userName;
    private String email;

    public CustomerDTO() {}
}
