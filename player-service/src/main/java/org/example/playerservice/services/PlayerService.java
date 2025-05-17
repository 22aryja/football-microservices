package org.example.playerservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.playerservice.dto.PlayerDto;
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

    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<Player> getAllPlayersInTeam(UUID teamId) {
        Optional<Team> possibleTeam = teamRepository.findById(teamId);

        if (possibleTeam.isPresent()) {
            return playerRepository.getAllPlayersInTeam(teamId);
        } else {
            throw new EntityNotFoundException("Team not found with id: " + teamId);
        }
    }

    public Player getPlayerById(UUID playerId) {
        return playerRepository.findById(playerId).orElseThrow(() ->
                new IllegalStateException("Player with such id is not found")
        );
    }

    @Transactional
    public Player createPlayerInTeam(UUID teamId, PlayerDto request){
        Team team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        Player player = new Player();
        fillPlayerFields(player, request, team);

        return playerRepository.save(player);
    }

    @Transactional
    public Player updatePlayer(UUID playerId, PlayerDto request, UUID teamId) {
        Player player = playerRepository.findById(playerId)
                .orElseThrow(() -> new EntityNotFoundException("Player with such id is not found"));

        Team team;
        if (teamId != null) {
            team = teamRepository.findById(teamId)
                    .orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));
        } else {
            team = player.getTeam();
        }

        fillPlayerFields(player, request, team);
        return playerRepository.save(player);
    }

    public void deletePlayer(UUID playerId) {
        Optional<Player> possiblePlayer = playerRepository.findById(playerId);

        if (possiblePlayer.isPresent()) {
            playerRepository.deleteById(playerId);
        } else {
            throw new EntityNotFoundException("Player with such id does not exist");
        }
    }

    private void fillPlayerFields(Player player, PlayerDto request, Team foundTeam){
        player.setFirstName(request.getFirstName());
        player.setLastName(request.getLastName());
        player.setBirthdate(request.getBirthdate());
        player.setSalary(request.getSalary());
        player.setTShirtNumber(request.getTShirtNumber());
        player.setTeam(foundTeam);
    }
}
