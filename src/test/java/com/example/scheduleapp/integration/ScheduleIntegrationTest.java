package com.example.scheduleapp.integration;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.repository.ScheduleRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class ScheduleIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ScheduleRepository scheduleRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("일정 전체 목록 조회 success")
    void testGetAllSchedule() throws Exception {
        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(view().name("schedules/list"));
    }

    @Test
    @DisplayName("새 일정 등록 성공")
    void testCreateSchedule() throws Exception {
        ScheduleEntity schedule = ScheduleEntity.builder()
                .title("통합 테스트 제목")
                .description("통합 테스트 설명")
                .dueDate(LocalDateTime.now().plusDays(1))
                .createdAt(LocalDateTime.now())
                .build();

        scheduleRepository.save(schedule);
        mockMvc.perform(get("/schedules"))
                .andExpect(status().isOk())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("통합 테스트 제목")));
    }
}
