package org.example.countryservice.repository;

import org.example.countryservice.models.League;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface LeagueRepository extends JpaRepository<League,UUID> {

    @Query("SELECT name from League WHERE name = ?1")
    Optional<League> findLeagueByName(String name);
}
