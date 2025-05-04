package com.example.scheduleapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class ScheduleResponse {
    private Long id;

    private String title;
    private String description;
    private boolean completed;


    private String dueDate;
    private String createdAt;
}