package com.example.scheduleapp.controller;

import com.example.scheduleapp.dto.ScheduleEntity;
import com.example.scheduleapp.dto.ScheduleRequest;
import com.example.scheduleapp.dto.ScheduleResponse;
import com.example.scheduleapp.service.ScheduleService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.tomcat.util.descriptor.LocalResolver;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.LocaleResolver;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
@RequestMapping("/schedules")
public class ScheduleController {

    private final ScheduleService scheduleService;
    private final MessageSource messageSource;
    private final LocaleResolver localeResolver;

    //목록 보기
    @GetMapping
    public String list(@RequestParam(required = false) String keyword,
                       @RequestParam(required = false) String status,
                       Model model) {
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

        // 키워드
        if (keyword != null && !keyword.isBlank()) {
            allSchedules = allSchedules.stream()
                    .filter(s -> s.getTitle() != null && s.getTitle().contains(keyword))
                    .collect(Collectors.toList());
        }
        // 상태
        if (status != null) {
            if (status.equals("completed")) {
                allSchedules = allSchedules.stream()
                        .filter(ScheduleResponse::isCompleted)
                        .collect(Collectors.toList());
            } else if (status.equals("incomplete")) {
                allSchedules = allSchedules.stream()
                        .filter(s -> !s.isCompleted())
                        .collect(Collectors.toList());
            }
        }

        model.addAttribute("keyword", keyword == null ? "" : keyword);
        model.addAttribute("status", status == null ? "" : status);
        model.addAttribute("equalsCompleted", "completed".equals(status));
        model.addAttribute("equalsIncomplete", "incomplete".equals(status));
        model.addAttribute("scheduleList", allSchedules);
        return "schedules/list";
    }



    // 등록 폼
    @GetMapping("/new")
    public String form(HttpServletRequest request, Model model){
        ScheduleRequest schedule = new ScheduleRequest();
        schedule.setTitle("");
        schedule.setDescription("");
        schedule.setDueDate(LocalDateTime.now());

        Locale locale = localeResolver.resolveLocale(request);

        model.addAttribute("msg", Map.of(
                "title", messageSource.getMessage("schedule.form.label.title", null, locale),
                "description", messageSource.getMessage("schedule.form.label.description", null, locale),
                "dueDate", messageSource.getMessage("schedule.form.label.dueDate", null, locale),
                "completed", messageSource.getMessage("schedule.form.label.completed", null, locale),
                "save", messageSource.getMessage("schedule.form.button.save", null, locale),
                "back", messageSource.getMessage("schedule.form.button.back", null, locale)
        ));
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
                            Collectors.mapping(
                                    fe -> messageSource.getMessage(fe, LocaleContextHolder.getLocale()),
                                    Collectors.toList()
                            )
                    ));
            model.addAttribute("fieldErrors", fieldErrors);

            // 전역 오류도 따로 전달
            var globalErrors = result.getGlobalErrors().stream()
                    .map(err -> messageSource.getMessage(err, LocaleContextHolder.getLocale()))
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
