package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.dto.ScheduleRequest;
import com.example.scheduleapp.dto.ScheduleResponse;
import com.example.scheduleapp.service.ScheduleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;

    //목록 보기
    @GetMapping
    public String list(Model model) {
        List<ScheduleResponse> responses = scheduleService.getAllSchedules().stream()
                .map(entity -> {
                    ScheduleResponse dto = new ScheduleResponse();
                    dto.setId(entity.getId());
                    dto.setTitle(entity.getTitle());
                    dto.setDescription(entity.getDescription());
                    dto.setCompleted(entity.isCompleted());
                    dto.setDueDate(
                            entity.getDueDate() != null ?
                                    entity.getDueDate().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")) :
                                    "없음"
                    );
                    return dto;
                }).collect(Collectors.toList());

        model.addAttribute("scheduleList", responses);
        return "schedules/list";
    }


    // 등록 폼
    @GetMapping("/new")
    public String form(Model model){
        ScheduleRequest schedule = new ScheduleRequest();
        schedule.setTitle("");
        schedule.setDescription("");
        schedule.setDueDate(LocalDateTime.now());
        model.addAttribute("schedule", schedule);
        return "schedules/form";
    }

    // 수정 폼
    @GetMapping("/{id}/update")
    public String updateForm(@PathVariable Long id, Model model){
        ScheduleEntity schedule = scheduleService.getScheduleById(id)
                .orElseThrow(() -> new IllegalArgumentException("일정을 찾을 수 없습니다."));

        ScheduleRequest form = new ScheduleRequest();
        form.setId(schedule.getId());
        form.setTitle(schedule.getTitle());
        form.setDescription(schedule.getDescription());
        form.setCompleted(schedule.isCompleted());
        form.setDueDate(schedule.getDueDate());

        model.addAttribute("schedule", form);
        return "schedules/form";
    }

    // 글 저장 (등록/수정)
    @PostMapping
    public String save(@ModelAttribute ScheduleRequest form){
        ScheduleEntity schedule = ScheduleEntity.builder()
                .id(form.getId())
                .title(form.getTitle())
                .description(form.getDescription())
                .completed(form.isCompleted())
                .dueDate(form.getDueDate())
                .createdAt(LocalDateTime.now())
                .build();

        scheduleService.createSchedule(schedule);
        return "redirect:/schedules";
    }

    // 삭제
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id){
        scheduleService.deleteSchedule(id);
        return "redirect:/schedules";
    }


//    @GetMapping("/test")
//    public String testSimpleValue(Model model) {
//        model.addAttribute("title", "테스트 제목입니다.");
//        return "schedules/test"; // ← 템플릿 파일명: test.mustache
//    }
}
