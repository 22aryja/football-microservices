package org.example.playerservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.playerservice.client.TeamClient;
import org.example.playerservice.dto.PlayerDto;
import org.example.playerservice.dto.TeamDto;
import org.example.playerservice.models.Player;
import org.example.playerservice.repository.PlayerRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamClient teamClient;

    public PlayerService(PlayerRepository playerRepository, TeamClient teamClient) {
        this.playerRepository = playerRepository;
        this.teamClient = teamClient;
    }

    public List<Player> getAllPlayersInTeam(UUID teamId) {
        TeamDto team = teamClient.getTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team not found with id: " + teamId);
        }
        return playerRepository.getAllPlayersInTeam(teamId);
    }

    public Player getPlayerById(UUID playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() ->
                new IllegalStateException("Player with such id is not found")
        );
        enrichPlayerWithTeamData(player);
        return player;
    }

    @Transactional
    public Player createPlayerInTeam(UUID teamId, PlayerDto request) {
        TeamDto team = teamClient.getTeamById(teamId);
        if (team == null) {
            throw new EntityNotFoundException("Team not found with id: " + teamId);
        }

        Player player = new Player();
        fillPlayerFields(player, request, teamId);
        player = playerRepository.save(player);
        player.setTeam(team);
        return player;
    }

    @Transactional
    public Player updatePlayer(UUID playerId, PlayerDto request, UUID teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with such id is not found"));

        UUID finalTeamId = teamId != null ? teamId : player.getTeamId();
        TeamDto team = teamClient.getTeamById(finalTeamId);
        if (team == null) {
            throw new EntityNotFoundException("Team not found with id: " + finalTeamId);
        }

        fillPlayerFields(player, request, finalTeamId);
        player = playerRepository.save(player);
        player.setTeam(team);
        return player;
    }

    public void deletePlayer(UUID playerId) {
        if (!playerRepository.existsById(playerId)) {
            throw new EntityNotFoundException("Player with such id does not exist");
        }
        playerRepository.deleteById(playerId);
    }

    private void fillPlayerFields(Player player, PlayerDto request, UUID teamId) {
        player.setFirstName(request.getFirstName());
        player.setLastName(request.getLastName());
        player.setBirthdate(request.getBirthdate());
        player.setSalary(request.getSalary());
        player.setTShirtNumber(request.getTShirtNumber());
        player.setTeamId(teamId);
    }

    private void enrichPlayerWithTeamData(Player player) {
        try {
            TeamDto team = teamClient.getTeamById(player.getTeamId());
            player.setTeam(team);
        } catch (Exception e) {
            System.err.println("Error enriching player data: " + e.getMessage());
        }
    }
}
