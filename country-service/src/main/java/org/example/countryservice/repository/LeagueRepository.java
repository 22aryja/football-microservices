package org.example.countryservice.repository;

import org.example.countryservice.models.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League,UUID> {

    @Query("SELECT name from League WHERE name = ?1")
    Optional<League> findLeagueByName(String name);

    @Query("SELECT l FROM League l LEFT JOIN FETCH l.teams WHERE l.id = :id")
    Optional<League> findLeagueWithTeamsById(UUID id);

    @Query("SELECT DISTINCT l FROM League l LEFT JOIN FETCH l.teams")
    List<League> findAllLeagueWithTeams();
}
