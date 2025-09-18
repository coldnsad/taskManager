package com.example.taskManager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    Long id;
    @NotBlank(message = "Title is blank")
    @Column(nullable = false)
    String title;
    String description;
    @Column(columnDefinition = "boolean default false")
    Boolean completed;
    @CreationTimestamp
    Date creationDate;
    Long customerId;
}
