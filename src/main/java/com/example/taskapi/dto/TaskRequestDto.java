package com.example.taskapi.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class TaskRequestDto {

    @NotBlank(message = "Title must not be blank")
    private String title;

    private String description;

    @NotBlank(message = "Status must not be blank")
    private String status;

    @NotNull(message = "User id must not be null")
    private Long userId;

    public TaskRequestDto() {
    }

    public TaskRequestDto(String title, String description, String status, Long userId) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getStatus() {
        return status;
    }

    public Long getUserId() {
        return userId;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}