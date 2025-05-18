package org.example.teamservice.services;
import org.example.teamservice.client.CountryClient;
import org.example.teamservice.dto.CoachDto;
import org.example.teamservice.dto.CountryDto;
import org.example.teamservice.models.Coach;
import org.example.teamservice.models.Team;
import org.example.teamservice.repository.CoachRepository;
import org.example.teamservice.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final CountryClient countryClient;
    private final TeamRepository teamRepository;

    public CoachService(CoachRepository coachRepository, CountryClient countryClient, TeamRepository teamRepository) {
        this.coachRepository = coachRepository;
        this.countryClient = countryClient;
        this.teamRepository = teamRepository;
    }

    public List<Coach> getAllCoaches() {
        List<Coach> coaches = coachRepository.findAll();
        coaches.forEach(this::enrichCoachWithCountryData);
        return coaches;
    }

    @Transactional
    public Coach createCoach(CoachDto request) {
        CountryDto country = countryClient.getCountryById(request.getCountryId());
        if (country == null) {
            throw new EntityNotFoundException("Country with such id does not exist");
        }

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team with such id does not exist"));

        Coach newCoach = new Coach();
        newCoach.setFirstName(request.getFirstName());
        newCoach.setLastName(request.getLastName());
        newCoach.setBirthdate(request.getBirthdate());
        newCoach.setSalary(request.getSalary());
        newCoach.setCountryFromId(request.getCountryId());
        newCoach.setTeam(team);
        newCoach.setCountryFrom(country);

        return coachRepository.save(newCoach);
    }

    public void deleteCoach(UUID coachId) {
        if(!coachRepository.existsById(coachId)) {
            throw new EntityNotFoundException("Coach with such id does not exist");
        }
        coachRepository.deleteById(coachId);
    }

    private void enrichCoachWithCountryData(Coach coach) {
        try {
            CountryDto country = countryClient.getCountryById(coach.getCountryFromId());
            coach.setCountryFrom(country);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Error enriching coach data: " + e.getMessage());
        }
    }
}
