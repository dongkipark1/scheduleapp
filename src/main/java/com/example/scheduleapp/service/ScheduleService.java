package com.example.scheduleapp.service;

import com.example.scheduleapp.dto.Schedule;
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
    public Schedule createSchedule(Schedule schedule){
        return scheduleRepository.save(schedule);
    }

    //전체 일정 조회
    public List<Schedule> getAllSchedules() {
        return scheduleRepository.findAll();
    }

    // ID로 일정 조회
    public Optional<Schedule> getScheduleById(Long id) {
        return scheduleRepository.findById(id);
    }

    // 일정 수정
    public Schedule updateSchedule(Long id, Schedule updatedSchedule) {
        Schedule schedule = scheduleRepository.findById(id)
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
