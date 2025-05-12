package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.*;
import com.example.scheduleapp.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/schedules")
@RequiredArgsConstructor
public class ScheduleRestController {

    private final ScheduleService scheduleService;

    //일정 등록
    @PostMapping
    public ResponseEntity<CommonResponse<ScheduleEntity>> create(@RequestBody ScheduleEntity schedule){
        ScheduleEntity saved = scheduleService.createSchedule(schedule);
        return ResponseEntity.ok(new CommonResponse<>(true, "일정이 등록되었습니다.", saved));
    }

    //전체 일정 조회
    @GetMapping
    public ResponseEntity<CommonResponse<List<ScheduleEntity>>> getAll(){
        List<ScheduleEntity> schedules = scheduleService.getAllSchedules();
        return ResponseEntity.ok(new CommonResponse<>(true,"전체 일정 조회 성공",schedules));
    }

    //특정 일정 조회
    @GetMapping("/{id}")
    public ResponseEntity<ScheduleEntity> getById(@PathVariable Long id){
        return scheduleService.getScheduleById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    //일정 수정
    @PutMapping("/{id}")
    public ResponseEntity<ScheduleEntity> update(@PathVariable Long id, @RequestBody ScheduleEntity schedule){
        ScheduleEntity updated = scheduleService.updateSchedule(id, schedule);
        return ResponseEntity.ok(updated);
    }

    //일정 삭제
    @DeleteMapping
    public ResponseEntity<Void> delete(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return ResponseEntity.noContent().build();
    }
}
