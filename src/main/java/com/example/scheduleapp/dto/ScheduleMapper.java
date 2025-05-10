package com.example.scheduleapp.dto;

import java.time.format.DateTimeFormatter;

public class ScheduleMapper {

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 mm분");

    public static ScheduleEntity toEntity(ScheduleRequest dto){
        return ScheduleEntity.builder()
                .id(dto.getId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .completed(dto.isCompleted())
                .dueDate(dto.getDueDate())
                .createdAt(java.time.LocalDateTime.now())
                .build();
    }

    public static ScheduleResponse toResponse(ScheduleEntity entity){
        ScheduleResponse dto = new ScheduleResponse();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setDescription(entity.getDescription());
        dto.setCompleted(entity.isCompleted());
        dto.setDueDate(entity.getDueDate() != null ? entity.getDueDate().format(formatter) : "없음");
        dto.setCreatedAt(entity.getCreatedAt() != null ? entity.getCreatedAt().format(formatter) : "미정");
        return dto;
    }
}
