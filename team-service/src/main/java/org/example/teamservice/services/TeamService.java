package org.example.teamservice.services;

import com.example.football.models.League;
import com.example.football.models.Team;
import com.example.football.repository.LeagueRepository;
import com.example.football.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TeamService {

    private final TeamRepository teamRepository;
    private final LeagueRepository leagueRepository;

    public TeamService(TeamRepository teamRepository, LeagueRepository leagueRepository) {
        this.teamRepository = teamRepository;
        this.leagueRepository = leagueRepository;
    }

    public List<Team> getTeams() {
        return teamRepository.findAll();
    }

    public Team getTeamById(UUID teamId) {
        return teamRepository.findTeamWithStadiumById(teamId).orElseThrow(() ->
                new EntityNotFoundException("Country not found with id: " + teamId)
        );
    }

    @Transactional
    public Team createTeam(String name, Integer amountOfPlayers, UUID leagueId) {
        Optional<Team> possibleTeam = teamRepository.findTeamByName(name);
        possibleTeam.ifPresent(team -> {
            throw new IllegalStateException("Such team already exists");
        });

        Team newTeam = new Team();
        newTeam.setName(name);
        newTeam.setAmountOfPlayers(amountOfPlayers);
        League league = leagueRepository.findById(leagueId).
                orElseThrow(() -> new EntityNotFoundException("League not found with id: " + leagueId));
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
}
