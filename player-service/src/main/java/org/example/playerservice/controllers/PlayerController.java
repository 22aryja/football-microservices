package org.example.playerservice.controllers;

import org.example.playerservice.dto.PlayerDto;
import org.example.playerservice.models.Player;
import org.example.playerservice.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/player")
public class PlayerController {

    private final PlayerService playerService;

    @Autowired
    public PlayerController(PlayerService playerService) {
        this.playerService = playerService;
    }

    @GetMapping
    public ResponseEntity<List<Player>> getAllPlayersInTeam(@RequestParam UUID teamId) {
        List<Player> players = playerService.getAllPlayersInTeam(teamId);
        return ResponseEntity.ok(players);
    }

    @GetMapping(path = "{playerId}")
    public ResponseEntity<Player> getPlayerById(@PathVariable UUID playerId) {
        Player player = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(player);
    }

    @PostMapping
    public ResponseEntity<Player> createPlayerInTeam(@RequestParam UUID teamId, @ModelAttribute PlayerDto request) {
        Player player = playerService.createPlayerInTeam(teamId, request);
        return ResponseEntity.ok(player);
    }

    @PatchMapping(path = "{playerId}")
    public ResponseEntity<Player> updatePlayer(
            @PathVariable UUID playerId,
            @ModelAttribute PlayerDto request,
            @RequestParam(required = false) UUID teamId
    ) {
        Player player = playerService.updatePlayer(playerId, request, teamId);
        return ResponseEntity.ok(player);
    }

    @DeleteMapping(path = "{playerId}")
    public ResponseEntity<Void> deletePlayer(@PathVariable UUID playerId){
        playerService.deletePlayer(playerId);
        return ResponseEntity.noContent().build();
    }
}
