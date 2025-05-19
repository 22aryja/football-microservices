package org.example.countryservice.controllers;

import org.example.countryservice.models.League;
import org.example.countryservice.services.LeagueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/country/league")
public class LeagueController {

    private final LeagueService leagueService;

    @Autowired
    public LeagueController(LeagueService leagueService) {
        this.leagueService = leagueService;
    }

    @GetMapping
    public ResponseEntity<List<League>> getLeagues () {
        List<League> leagues = leagueService.getLeagues();
        return ResponseEntity.ok(leagues);
    }

    @GetMapping(path = "{leagueId}")
    public ResponseEntity<League> getLeague (@PathVariable UUID leagueId) {
        League league = leagueService.getLeagueById(leagueId);
        return ResponseEntity.ok(league);
    }

    @PostMapping
    public ResponseEntity<League> createLeague(@RequestParam String name, @RequestParam Integer amountOfTeams, @RequestParam UUID countryId) {
        League league = leagueService.createLeague(name, amountOfTeams, countryId);
        return ResponseEntity.status(201).body(league);
    }

    @PatchMapping(path = "{leagueId}")
    public ResponseEntity<League> updateLeague(@PathVariable UUID leagueId, @RequestParam String name, @RequestParam Integer amountOfTeams) {
        League league = leagueService.updateLeague(leagueId, name, amountOfTeams);
        return ResponseEntity.ok(league);
    }

    @DeleteMapping(path = "{leagueId}")
    public ResponseEntity<Void> deleteLeague(@PathVariable UUID leagueId) {
        leagueService.deleteLeague(leagueId);
        return ResponseEntity.noContent().build();
    }
}
