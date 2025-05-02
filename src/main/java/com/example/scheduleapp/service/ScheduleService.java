package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;


    // 일정 등록
    public ScheduleEntity createSchedule(ScheduleEntity schedule){
        return scheduleRepository.save(schedule);
    }

    //전체 일정 조회
    public List<ScheduleEntity> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // ID로 일정 조회
    public Optional<ScheduleEntity> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // 일정 수정
    public ScheduleEntity updateSchedule(Long id, ScheduleEntity updatedSchedule) {
        ScheduleEntity schedule = scheduleRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        schedule.setTitle(updatedSchedule.getTitle());
        schedule.setDescription(updatedSchedule.getDescription());
        schedule.setCompleted(updatedSchedule.isCompleted());
        schedule.setDueDate(updatedSchedule.getDueDate());

        return scheduleRepository.save(schedule);
    }

    // 일정 삭제
    public void deleteSchedule(Long id) {
        scheduleRepository.deleteById(id);
    }
}
