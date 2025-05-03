package com.example.scheduleapp.repository;

import com.example.scheduleapp.dto.ScheduleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScheduleRepository extends JpaRepository<ScheduleEntity,Long> {

    List<ScheduleEntity> findByTitleContainingIgnoreCase(String keyword);

}
