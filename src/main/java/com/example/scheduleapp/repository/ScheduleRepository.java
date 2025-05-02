package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {
}
