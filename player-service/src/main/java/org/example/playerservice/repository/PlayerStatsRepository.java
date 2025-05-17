package org.example.playerservice.repository;

import org.example.playerservice.models.PlayerStats;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PlayerStatsRepository extends JpaRepository<PlayerStats,UUID> {
}
