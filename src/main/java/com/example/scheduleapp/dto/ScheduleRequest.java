package com.example.scheduleapp.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Data
public class ScheduleRequest {

    @Schema(description = "일정 ID (수정 시에만 사용)", example = "1")
    private Long id;

    @Schema(description = "일정 제목", example = "일정 준비")
    @NotBlank(message = "{schedule.title.required}")
    private String title;

    @Schema(description = "일정 설명", example = "일정 내용")
    private String description;

    @Schema(description = "완료 여부", example = "false")
    private boolean completed;

    @Schema(description = "마감일", example = "2025-05-20T18:00:00")
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm")
    @Future(message = "{schedule.dueDate.future}")
    private LocalDateTime dueDate;
}
