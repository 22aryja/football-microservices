package org.example.teamservice.services;
import com.example.football.dto.CoachDto;
import com.example.football.models.Coach;
import com.example.football.models.Country;
import com.example.football.models.Team;
import com.example.football.repository.CoachRepository;
import com.example.football.repository.CountryRepository;
import com.example.football.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class CoachService {

    private final CoachRepository coachRepository;
    private final CountryRepository countryRepository;

    private final TeamRepository teamRepository;

    public CoachService(CoachRepository coachRepository, CountryRepository countryRepository, TeamRepository teamRepository) {
        this.coachRepository = coachRepository;
        this.countryRepository = countryRepository;
        this.teamRepository = teamRepository;
    }

    public List<Coach> getAllCoaches(){
        return coachRepository.findAll();
    }

    @Transactional
    public Coach createCoach(CoachDto request) {
        Country country = countryRepository.findById(request.getCountryId())
                .orElseThrow(() -> new EntityNotFoundException("Country with such id does not exist"));

        Team team = teamRepository.findById(request.getTeamId())
                .orElseThrow(() -> new EntityNotFoundException("Team with such id does not exist"));

        Coach newCoach = new Coach();
        newCoach.setFirstName(request.getFirstName());
        newCoach.setLastName(request.getLastName());
        newCoach.setBirthdate(request.getBirthdate());
        newCoach.setSalary(request.getSalary());
        newCoach.setCountryFrom(country);
        newCoach.setTeam(team);

        return coachRepository.save(newCoach);
    }

    public void deleteCoach(UUID coachId) {
        if(!coachRepository.existsById(coachId)){
            throw new EntityNotFoundException("Coach with such id does not exist");
        }

        coachRepository.deleteById(coachId);
    }
}
