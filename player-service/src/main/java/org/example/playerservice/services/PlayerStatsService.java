package org.example.playerservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.playerservice.dto.PlayerStatsDto;
import org.example.playerservice.models.Player;
import org.example.playerservice.models.PlayerStats;
import org.example.playerservice.repository.PlayerRepository;
import org.example.playerservice.repository.PlayerStatsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
public class PlayerStatsService {

    private final PlayerStatsRepository playerStatsRepository;
    private final PlayerRepository playerRepository;

    public PlayerStatsService(PlayerStatsRepository playerStatsRepository, PlayerRepository playerRepository) {
        this.playerStatsRepository = playerStatsRepository;
        this.playerRepository = playerRepository;
    }

    @Transactional
    public PlayerStats addStatsToPlayer(UUID playerId, PlayerStatsDto stats){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player is not found"));

        PlayerStats playerStats = new PlayerStats();
        playerStats.setGoals(stats.getGoals());
        playerStats.setAssists(stats.getAssists());
        playerStats.setYellowCards(stats.getYellowCards());
        playerStats.setRedCards(stats.getRedCards());
        playerStats.setPlayer(player);

        return playerStatsRepository.save(playerStats);
    }

    @Transactional
    public PlayerStats updatePlayerStats(UUID playerId, PlayerStatsDto stats){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player is not found"));

        PlayerStats existingStats = player.getPlayerStats();
        if (existingStats == null) {
            throw new EntityNotFoundException("Stats not found for this player");
        }

        existingStats.setGoals(stats.getGoals());
        existingStats.setAssists(stats.getAssists());
        existingStats.setYellowCards(stats.getYellowCards());
        existingStats.setRedCards(stats.getRedCards());

        return playerStatsRepository.save(existingStats);
    }

    public void deletePlayerStats(UUID playerId){
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player is not found"));

        PlayerStats stats = player.getPlayerStats();
        if (stats != null) {
            player.setPlayerStats(null);
            playerStatsRepository.deleteById(stats.getId());
        }
    }
}
