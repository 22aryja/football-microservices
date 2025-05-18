package org.example.teamservice.services;

import org.example.teamservice.client.LeagueClient;
import org.example.teamservice.dto.LeagueDto;
import org.example.teamservice.models.Team;
import org.example.teamservice.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final LeagueClient leagueClient;

    public TeamService(TeamRepository teamRepository, LeagueClient leagueClient) {
        this.teamRepository = teamRepository;
        this.leagueClient = leagueClient;
    }

    public List<Team> getTeams() {
        List<Team> teams = teamRepository.findAll();
        teams.forEach(this::enrichTeamWithLeagueData);
        return teams;
    }

    public Team getTeamById(UUID teamId) {
        Team team = teamRepository.findTeamWithStadiumById(teamId).orElseThrow(() ->
                new EntityNotFoundException("Team not found with id: " + teamId)
        );
        enrichTeamWithLeagueData(team);
        return team;
    }

    @Transactional
    public Team createTeam(String name, Integer amountOfPlayers, UUID leagueId) {
        Optional<Team> possibleTeam = teamRepository.findTeamByName(name);
        possibleTeam.ifPresent(team -> {
            throw new IllegalStateException("Such team already exists");
        });

        LeagueDto league = leagueClient.getLeagueById(leagueId);
        if (league == null) {
            throw new EntityNotFoundException("League not found with id: " + leagueId);
        }

        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setAmountOfPlayers(amountOfPlayers);
        newTeam.setLeagueId(leagueId);
        newTeam.setLeague(league);

        return teamRepository.save(newTeam);
    }

    @Transactional
    public Team updateTeam(UUID teamId, String name, Integer amountOfPlayers) {
        Optional<Team> possibleTeam = teamRepository.findById(teamId);

        if (possibleTeam.isPresent()) {
            Team foundTeam = possibleTeam.get();

            foundTeam.setName(name);
            foundTeam.setAmountOfPlayers(amountOfPlayers);

            return teamRepository.save(foundTeam);
        } else {
            throw new EntityNotFoundException("Team not found with id: " + teamId);
        }
    }

    public void deleteTeam(UUID teamId) {
        if (!teamRepository.existsById(teamId)) {
            throw new EntityNotFoundException("Team with such id does not exist");
        }
        teamRepository.deleteById(teamId);
    }

    private void enrichTeamWithLeagueData(Team team) {
        try {
            LeagueDto league = leagueClient.getLeagueById(team.getLeagueId());
            team.setLeague(league);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Error enriching team data: " + e.getMessage());
        }
    }
}
