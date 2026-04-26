package com.emergency.response_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.emergency.response_system.model.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}