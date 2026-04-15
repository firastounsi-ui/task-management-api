package com.example.taskapi.service;

import com.example.taskapi.dto.TaskRequestDto;
import com.example.taskapi.dto.TaskResponseDto;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.User;
import com.example.taskapi.exception.ResourceNotFoundException;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private TaskService taskService;

    @Test
    void createTask_shouldCreateTaskSuccessfully() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus("OPEN");
        request.setUserId(1L);

        User user = new User();
        user.setId(1L);
        user.setName("User");
        user.setEmail("user@example.com");
        user.setRole("USER");

        Task savedTask = new Task();
        savedTask.setId(1L);
        savedTask.setTitle("Test Task");
        savedTask.setDescription("Test Description");
        savedTask.setStatus("OPEN");
        savedTask.setUser(user);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.save(any(Task.class))).thenReturn(savedTask);

        TaskResponseDto response = taskService.createTask(request);

        assertNotNull(response);
        assertEquals(1L, response.getId());
        assertEquals("Test Task", response.getTitle());
        assertEquals("Test Description", response.getDescription());
        assertEquals("OPEN", response.getStatus());
    }

    @Test
    void createTask_shouldThrowResourceNotFoundException_whenUserDoesNotExist() {
        TaskRequestDto request = new TaskRequestDto();
        request.setTitle("Test Task");
        request.setDescription("Test Description");
        request.setStatus("OPEN");
        request.setUserId(999L);

        when(userRepository.findById(999L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> taskService.createTask(request));
    }
}