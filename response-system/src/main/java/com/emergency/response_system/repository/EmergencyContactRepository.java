package com.emergency.response_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emergency.response_system.model.EmergencyContact;

import java.util.List;

public interface EmergencyContactRepository extends JpaRepository<EmergencyContact, Long> {
    List<EmergencyContact> findByUserId(Long userId);
}