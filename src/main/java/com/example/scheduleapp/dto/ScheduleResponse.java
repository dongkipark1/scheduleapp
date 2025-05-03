package com.example.scheduleapp.dto;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;


@Data
public class ScheduleResponse {
    private Long id;

    @NotBlank(message = "제목은 필수입니다.")
    private String title;
    private String description;
    private boolean completed;

    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future
    private String dueDate;
}