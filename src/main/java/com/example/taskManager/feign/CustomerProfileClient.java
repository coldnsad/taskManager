package com.example.taskManager.feign;

import com.example.taskManager.dto.CustomerDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-profile-service")
public interface CustomerProfileClient {
    @GetMapping("/customer-api/customers/{id}")
    ResponseEntity<CustomerDTO> getCustomerById(@PathVariable Long id);
}
