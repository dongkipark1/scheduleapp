package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.Schedule;
import com.example.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //목록 보기
    @GetMapping
    public String list(Model model){
        model.addAttribute("scheduleList", scheduleService.getAllSchedules());
        return "schedule/list";
    }

    // 등록 폼
    @GetMapping("/new")
    public String form(Model model){
        model.addAttribute("schedule", new Schedule());
        return "schedule/form";
    }

    // 수정 폼
    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model){
        Schedule schedule = scheduleService.getScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));
        model.addAttribute("schedule", schedule);
        return "schedule/form";
    }

    // 글 저장 (등록/수정)
    @PostMapping
    public String save(@ModelAttribute Schedule schedule){
        scheduleService.createSchedule(schedule);
        return "redirect:/schedules";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";
    }

}
