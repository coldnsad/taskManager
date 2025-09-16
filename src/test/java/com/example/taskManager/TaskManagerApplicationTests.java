package com.example.taskManager;

import com.example.taskManager.exceptions.TaskNotFoundException;
import com.example.taskManager.services.TaskServiceImpl;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class TaskManagerApplicationTests {

    @Autowired
    private TaskServiceImpl taskServiceImpl;

	@Test
	void contextLoads() {
	}

	@Test
	void getTaskById_shouldThrowExceptionIfTaskNotExists() {
		Long nonExistentId = 999L;

		// Проверяем, что выбросится нужное исключение
		assertThrows(TaskNotFoundException.class,
				() -> taskServiceImpl.getTaskById(nonExistentId));
	}

}
