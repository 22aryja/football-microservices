package org.example.countryservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.countryservice.client.TeamClient;
import org.example.countryservice.models.Country;
import org.example.countryservice.models.League;
import org.example.countryservice.repository.CountryRepository;
import org.example.countryservice.repository.LeagueRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class LeagueService {

    private final LeagueRepository leagueRepository;
    private final CountryRepository countryRepository;

    private final TeamClient teamClient;

    public LeagueService(LeagueRepository leagueRepository, CountryRepository countryRepository, TeamClient teamClient) {
        this.leagueRepository = leagueRepository;
        this.countryRepository = countryRepository;
        this.teamClient = teamClient;
    }

    public List<League> getLeagues() {
        return leagueRepository.findAll();
    }

    public League getLeagueById(UUID leagueId){
        return leagueRepository.findById(leagueId)
                .orElseThrow(
                () -> new EntityNotFoundException("Country not found with id: " + leagueId)
        );
    }

    @Transactional
    public League createLeague(String name, Integer amountOfTeams, UUID countryId){
        Optional<League> possibleLeague = leagueRepository.findLeagueByName(name);
        possibleLeague.ifPresent(league -> {
            throw new IllegalStateException("Such league already exists");
        });

        League newLeague = new League();
        newLeague.setName(name);
        newLeague.setAmountOfTeams(amountOfTeams);
        Country country = countryRepository.findById(countryId)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + countryId));
        newLeague.setCountry(country);

        return leagueRepository.save(newLeague);
    }

    @Transactional
    public League updateLeague(UUID leagueId, String name, Integer amountOfTeams) {
        Optional<League> possibleLeague = leagueRepository.findById(leagueId);

        if (possibleLeague.isPresent()) {
            League foundLeague = possibleLeague.get();

            foundLeague.setName(name);
            foundLeague.setAmountOfTeams(amountOfTeams);

            return leagueRepository.save(foundLeague);
        } else {
            throw new EntityNotFoundException("Country not found with id: " + leagueId);
        }
    }

    public void deleteLeague(UUID leagueId) {
        if (!leagueRepository.existsById(leagueId)) {
            throw new EntityNotFoundException("League with id " + leagueId + " not found");
        }
        leagueRepository.deleteById(leagueId);
    }
}
