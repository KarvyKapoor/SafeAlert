package com.emergency.response_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergency.response_system.model.PanicAlertRecord;

public interface PanicAlertRecordRepository extends JpaRepository<PanicAlertRecord, Long> {
    List<PanicAlertRecord> findByUserIdOrderByTriggeredAtDesc(Long userId);
}
