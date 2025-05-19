package org.example.playerservice.controllers;
import org.example.playerservice.dto.PlayerStatsDto;
import org.example.playerservice.models.PlayerStats;
import org.example.playerservice.services.PlayerStatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/player/stats")
public class PlayerStatsController {

    private final PlayerStatsService playerStatsService;

    @Autowired
    public PlayerStatsController(PlayerStatsService playerStatsService) {
        this.playerStatsService = playerStatsService;
    }

    @PostMapping
    public ResponseEntity<PlayerStats> addStatsToPlayer(@RequestParam UUID playerId, @ModelAttribute PlayerStatsDto request){
        PlayerStats playerStats = playerStatsService.addStatsToPlayer(playerId, request);
        return ResponseEntity.ok(playerStats);
    }

    @PatchMapping
    public ResponseEntity<PlayerStats> updateStats(@RequestParam UUID playerId, @ModelAttribute PlayerStatsDto request){
        PlayerStats playerStats = playerStatsService.updatePlayerStats(playerId, request);
        return ResponseEntity.ok(playerStats);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteStats(@RequestParam UUID playerId){
        playerStatsService.deletePlayerStats(playerId);
        return ResponseEntity.noContent().build();
    }
}
