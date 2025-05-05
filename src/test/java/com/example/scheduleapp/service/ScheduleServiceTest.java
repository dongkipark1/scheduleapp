package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.repository.ScheduleRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ScheduleServiceTest {

    @Mock
    private ScheduleRepository scheduleRepository;

    @InjectMocks
    private ScheduleService scheduleService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("일정 저장 테스트")
    void testCreateSchedule() {
        ScheduleEntity schedule = ScheduleEntity.builder()
                .title("제목 1")
                .description("내용 1")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);

        ScheduleEntity result = scheduleService.createSchedule(schedule);

        assertThat(result.getTitle()).isEqualTo("제목 1");
        verify(scheduleRepository, times(1)).save(schedule);
    }



}
