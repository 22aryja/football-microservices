package org.example.teamservice.controllers;
import com.example.football.models.Team;
import com.example.football.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/team")
public class TeamController {

    private final TeamService teamService;

    @Autowired
    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping
    public ResponseEntity<List<Team>> getTeams () {
        List<Team> teams = teamService.getTeams();
        return ResponseEntity.ok(teams);
    }

    @GetMapping(path = "{teamId}")
    public ResponseEntity<Team> getTeam(@PathVariable UUID teamId) {
        Team team = teamService.getTeamById(teamId);
        return ResponseEntity.ok(team);
    }

    @PostMapping
    public ResponseEntity<Team> createTeam(@RequestParam String name, @RequestParam Integer amountOfPlayers, @RequestParam UUID leagueId) {
        Team team = teamService.createTeam(name, amountOfPlayers, leagueId);
        return ResponseEntity.status(201).body(team);
    }

    @PatchMapping(path = "{teamId}")
    public ResponseEntity<Team> updateTeam(@PathVariable UUID teamId, @RequestParam String name, @RequestParam Integer amountOfPlayers) {
        Team team = teamService.updateTeam(teamId, name, amountOfPlayers);
        return ResponseEntity.ok(team);
    }

    @DeleteMapping(path = "{teamId}")
    public ResponseEntity<Void> deleteTeam(@PathVariable UUID teamId) {
        teamService.deleteTeam(teamId);
        return ResponseEntity.noContent().build();
    }
}
