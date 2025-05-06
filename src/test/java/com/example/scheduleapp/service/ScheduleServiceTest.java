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

    // given
    void testCreateSchedule() {
        ScheduleEntity schedule = ScheduleEntity.builder()
                .title("제목 1")
                .description("내용 1")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(schedule);

        // when
        ScheduleEntity result = scheduleService.createSchedule(schedule);

        // then
        assertThat(result.getTitle()).isEqualTo("제목 1");
        verify(scheduleRepository, times(1)).save(schedule);
    }

    @Test
    @DisplayName("일정 전체 목록 조회 테스트")
    void testGetAllSchedules() {

        //given
        ScheduleEntity schedule1 = ScheduleEntity.builder()
                .title("일정 1")
                .description("내용 1")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        ScheduleEntity schedule2 = ScheduleEntity.builder()
                .title("일정 2")
                .description("내용 2")
                .dueDate(LocalDateTime.now().plusDays(2))
                .createdAt(LocalDateTime.now())
                .build();

        List<ScheduleEntity> mockList = Arrays.asList(schedule1, schedule2);
        when(scheduleRepository.findAll()).thenReturn(mockList);

        //when
        List<ScheduleEntity> result = scheduleService.getAllSchedules();

        //then
        assertThat(result).hasSize(2);
        assertThat(result).extracting("title").contains("일정 1", "일정 2");
        verify(scheduleRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("일정 수정 테스트")
    void testUpdateSchedule(){
        //given
        Long scheduleId = 1L;

        ScheduleEntity original = ScheduleEntity.builder()
                .id(scheduleId)
                .title("기존 제목")
                .description("기존 설명")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .completed(false)
                .build();

        ScheduleEntity updated = ScheduleEntity.builder()
                .id(scheduleId)
                .title("수정 후 제목")
                .description("수정 후 설명")
                .dueDate(LocalDateTime.now().plusDays(2))
                .createdAt(original.getCreatedAt())
                .completed(true)
                .build();

        when(scheduleRepository.findById(scheduleId)).thenReturn(Optional.of(original));
        when(scheduleRepository.save(any(ScheduleEntity.class))).thenReturn(updated);

        //when
        ScheduleEntity result = scheduleService.createSchedule(updated);

        //then
        assertThat(result.getTitle()).isEqualTo("수정 후 제목");
        assertThat(result.isCompleted()).isTrue();
        verify(scheduleRepository, times(1)).save(updated);
    }

    @Test
    @DisplayName("일정 삭제 테스트")
    void testDeleteSchedule(){

        //given
        Long scheduleId = 1L;

        doNothing().when(scheduleRepository).deleteById(scheduleId);

        //when
        scheduleService.deleteSchedule(scheduleId);

        //then
        verify(scheduleRepository, times(1)).deleteById(scheduleId);
    }

    @Test
    @DisplayName("단일 일정 조회 테스트")
    void testGetScheduleById(){
        //given
        Long id = 1L;
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(id)
                .title("조회 할 제목")
                .description("조회 설명")
                .dueDate(LocalDateTime.now().plusDays(2))
                .createdAt(LocalDateTime.now())
                .build();

        when(scheduleRepository.findById(id)).thenReturn(Optional.of(schedule));

        //when
        Optional<ScheduleEntity> result = scheduleService.getScheduleById(id);

        //then
        assertThat(result).isPresent();
        assertThat(result.get().getId()).isEqualTo(id);
        assertThat(result.get().getTitle()).isEqualTo("조회 할 제목");

        verify(scheduleRepository, times(1)).findById(id);
    }
}
