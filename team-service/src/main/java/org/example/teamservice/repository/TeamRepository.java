package org.example.teamservice.repository;

import org.example.teamservice.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface TeamRepository extends JpaRepository<Team, UUID> {

    @Query("SELECT t from Team t WHERE t.name = :name")
    Optional<Team> findTeamByName(String name);

    @Query("SELECT t FROM Team t LEFT JOIN FETCH t.stadium WHERE t.id = :id")
    Optional<Team> findTeamWithStadiumById(UUID id);
}
