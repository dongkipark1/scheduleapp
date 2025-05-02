package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
}
