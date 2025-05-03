package com.example.scheduleapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {
    private Long id;

    @NotBlank(message = "{schedule.title.required}")
    private String title;

    private String description;
    private boolean completed;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "{schedule.dueDate.future}")
    private LocalDateTime dueDate;
}
