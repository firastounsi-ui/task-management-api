package com.example.taskapi.service;

import com.example.taskapi.dto.TaskRequestDto;
import com.example.taskapi.dto.TaskResponseDto;
import com.example.taskapi.entity.Task;
import com.example.taskapi.entity.User;
import com.example.taskapi.exception.ResourceNotFoundException;
import com.example.taskapi.repository.TaskRepository;
import com.example.taskapi.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    public TaskService(TaskRepository taskRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
    }

    public TaskResponseDto createTask(TaskRequestDto requestDto) {
        Optional<User> optionalUser = userRepository.findById(requestDto.getUserId());

        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("User not found with id: " + requestDto.getUserId());
        }

        User user = optionalUser.get();

        Task task = new Task();
        task.setTitle(requestDto.getTitle());
        task.setDescription(requestDto.getDescription());
        task.setStatus(requestDto.getStatus());
        task.setUser(user);

        Task savedTask = taskRepository.save(task);

        return mapToResponseDto(savedTask);
    }

    public List<TaskResponseDto> getAllTasks() {
        return taskRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    public TaskResponseDto getTaskById(Long id) {
        Task task = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        return mapToResponseDto(task);
    }

    public void deleteTask(Long id) {
        if (!taskRepository.existsById(id)) {
            throw new ResourceNotFoundException("Task not found with id: " + id);
        }
        taskRepository.deleteById(id);
    }

    private TaskResponseDto mapToResponseDto(Task task) {
        return new TaskResponseDto(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getStatus(),
                task.getUser().getId()
        );
    }
    public TaskResponseDto updateTask(Long id, TaskRequestDto requestDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Task not found with id: " + id));

        User user = userRepository.findById(requestDto.getUserId())
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + requestDto.getUserId()));

        existingTask.setTitle(requestDto.getTitle());
        existingTask.setDescription(requestDto.getDescription());
        existingTask.setStatus(requestDto.getStatus());
        existingTask.setUser(user);

        Task savedTask = taskRepository.save(existingTask);

        return mapToResponseDto(savedTask);
    }
}