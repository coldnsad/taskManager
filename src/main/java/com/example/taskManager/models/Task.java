package com.example.taskManager.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.util.Date;

@Entity
@Getter
@Setter
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @NotBlank(message = "Title is blank")
    @Column(nullable = false)
    private String title;
    private String description;
    @Column(columnDefinition = "boolean default false")
    private Boolean completed;
    @CreationTimestamp
    private Date creationDate;

}
