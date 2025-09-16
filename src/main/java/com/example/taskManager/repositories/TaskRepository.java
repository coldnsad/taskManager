package com.example.taskManager.repositories;

import com.example.taskManager.models.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task,Long>, JpaSpecificationExecutor<Task> {

    List<Task> findByCompleted(boolean completed);

    @Query("SELECT t FROM Task t WHERE LOWER(t.title) LIKE LOWER(concat('%', :keyword, '%'))")
    List<Task> findByTitleKeyword(@Param("keyword") String keyword);
}
