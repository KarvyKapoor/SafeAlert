package com.emergency.response_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emergency.response_system.model.ContactResponse;

public interface ContactResponseRepository extends JpaRepository<ContactResponse, Long> {
    List<ContactResponse> findByUserIdOrderByRespondedAtDesc(Long userId);
}
