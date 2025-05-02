package com.example.scheduleapp.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private LocalDateTime dueDate;
}
