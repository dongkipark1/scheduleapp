package com.example.scheduleapp.dto;

import lombok.Data;


@Data
public class ScheduleResponse {
    private Long id;
    private String title;
    private String description;
    private boolean completed;
    private String dueDate;
}