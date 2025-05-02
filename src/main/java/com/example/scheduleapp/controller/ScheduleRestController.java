package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping
    public ScheduleEntity create(@RequestBody ScheduleEntity schedule){
        return scheduleService.createSchedule(schedule);
    }

    //전체 일정 조회
    @GetMapping
    public List<ScheduleEntity> getAll(){
        return scheduleService.getAllSchedules();
    }

    //특정 일정 조회
    @GetMapping("/{id}")
    public ScheduleEntity getById(@PathVariable Long id){
        return scheduleService.getScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
    }

    //일정 수정
    @PutMapping("/{id}")
    public ScheduleEntity update(@PathVariable Long id, @RequestBody ScheduleEntity schedule){
        return scheduleService.updateSchedule(id, schedule);
    }

    //일정 삭제
    @DeleteMapping
    public void delete(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
    }
}
