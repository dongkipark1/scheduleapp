package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.dto.ScheduleRequest;
import com.example.scheduleapp.dto.ScheduleResponse;
import com.example.scheduleapp.service.ScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
    public String list(@RequestParam(required = false) String keyword, Model model) {
        List<ScheduleResponse> allSchedules = scheduleService.getAllSchedules().stream()
                .map(entity -> {
                    ScheduleResponse dto = new ScheduleResponse();
                    dto.setId(entity.getId());
                    dto.setTitle(entity.getTitle());
                    dto.setDescription(entity.getDescription());
                    dto.setCompleted(entity.isCompleted());
                    dto.setDueDate(
                            entity.getDueDate() != null ?
                                    entity.getDueDate().format(DateTimeFormatter.ofPattern("yyyy년 M월 d일 a h시 mm분")) :
                                    "없음"
                    );
                    return dto;
                })
                .collect(Collectors.toList());

        // 필터링
        if (keyword != null && !keyword.isBlank()) {
            allSchedules = allSchedules.stream()
                    .filter(s -> s.getTitle() != null && s.getTitle().contains(keyword))
                    .collect(Collectors.toList());
        }

        model.addAttribute("keyword", keyword == null ? "" : keyword); // ✅ 여기!
        model.addAttribute("scheduleList", allSchedules);
        return "schedules/list";
    }




    // 등록 폼
    @GetMapping("/new")
    public String form(Model model){
        ScheduleRequest schedule = new ScheduleRequest();
        schedule.setTitle("");
        schedule.setDescription("");
        schedule.setDueDate(LocalDateTime.now());
        model.addAttribute("now", LocalDateTime.now().toString());
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
        model.addAttribute("now", LocalDateTime.now().toString()); // ✅ 이거 추가해야 함!
        return "schedules/form";
    }


    // 글 저장 (등록/수정)
    @PostMapping
    public String save(@ModelAttribute @Valid ScheduleRequest form,
                       BindingResult result,
                       Model model) {
        if (result.hasErrors()) {
            // 에러 정보를 별도로 구성
            model.addAttribute("schedule", form);
            model.addAttribute("now", LocalDateTime.now().toString());

            // 필드 오류를 map 형태로 수집
            var fieldErrors = result.getFieldErrors().stream()
                    .collect(Collectors.groupingBy(
                            fe -> fe.getField(),
                            Collectors.mapping(DefaultMessageSourceResolvable::getDefaultMessage, Collectors.toList())
                    ));
            model.addAttribute("fieldErrors", fieldErrors);

            // 전역 오류도 따로 전달
            var globalErrors = result.getGlobalErrors().stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .toList();
            model.addAttribute("globalErrors", globalErrors);

            return "schedules/form";
        }

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
}
