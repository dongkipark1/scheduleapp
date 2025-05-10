package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.dto.ScheduleMapper;
import com.example.scheduleapp.dto.ScheduleRequest;
import com.example.scheduleapp.dto.ScheduleResponse;
import com.example.scheduleapp.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
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
    public ScheduleResponse create(@RequestBody @Valid ScheduleRequest request){
        ScheduleEntity entity = ScheduleMapper.toEntity(request);
        ScheduleEntity saved = scheduleService.createSchedule(entity);
        return ScheduleMapper.toResponse(saved);
    }

    //전체 일정 조회
    @GetMapping
    public List<ScheduleResponse> getAll(){
        return scheduleService.getAllSchedules().stream()
                .map(ScheduleMapper::toResponse)
                .collect(Collectors.toList());
    }

    //특정 일정 조회
    @GetMapping("/{id}")
    public ScheduleResponse getById(@PathVariable Long id){
        return scheduleService.getScheduleById(id)
                .map(ScheduleMapper::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
    }

    //일정 수정
    @PutMapping("/{id}")
    public ScheduleResponse update(@PathVariable Long id, @RequestBody @Valid ScheduleRequest request){
        ScheduleEntity updated = scheduleService.updateSchedule(id, ScheduleMapper.toEntity(request));
        return ScheduleMapper.toResponse(updated);
    }

    //일정 삭제
    @DeleteMapping
    public void delete(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
    }
}
