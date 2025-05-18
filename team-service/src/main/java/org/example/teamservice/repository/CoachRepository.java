package org.example.teamservice.repository;

import org.example.teamservice.models.Coach;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CoachRepository extends JpaRepository<Coach, UUID> {
}
