package org.example.playerservice.repository;


import org.example.playerservice.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface PlayerRepository extends JpaRepository<Player, UUID> {

    @Query("SELECT p FROM Player p WHERE p.teamId = :teamId")
    List<Player> getAllPlayersInTeam(UUID teamId);

}
