package org.example.teamservice.services;

import org.example.teamservice.dto.StadiumDto;
import org.example.teamservice.models.Stadium;
import org.example.teamservice.models.Team;
import org.example.teamservice.repository.StadiumRepository;
import org.example.teamservice.repository.TeamRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class StadiumService {

    private final StadiumRepository stadiumRepository;
    private final TeamRepository teamRepository;

    public StadiumService(StadiumRepository stadiumRepository, TeamRepository teamRepository) {
        this.stadiumRepository = stadiumRepository;
        this.teamRepository = teamRepository;
    }

    public List<Stadium> getStadiums() {
        return stadiumRepository.findAll();
    }

    public Stadium getStadium(UUID stadiumId) {
        return stadiumRepository.findById(stadiumId).orElseThrow(
                () -> new EntityNotFoundException("Stadium not found with id: " + stadiumId)
        );
    }

    @Transactional
    public Stadium createStadium(StadiumDto request, UUID teamId) {
        Team team = teamRepository.findById(teamId).
                orElseThrow(() -> new EntityNotFoundException("Team not found with id: " + teamId));

        Stadium stadium = new Stadium();
        stadium.setName(request.getName());
        stadium.setCity(request.getCity());
        stadium.setCapacity(request.getCapacity());
        stadium.setTeam(team);

        return stadiumRepository.save(stadium);
    }

    @Transactional
    public Stadium updateStadium(UUID stadiumId, StadiumDto request) {
        Optional<Stadium> possibleStadium = stadiumRepository.findById(stadiumId);

        if (possibleStadium.isPresent()) {
            Stadium foundStadium = possibleStadium.get();

            foundStadium.setName(request.getName());
            foundStadium.setCity(request.getCity());
            foundStadium.setCapacity(request.getCapacity());

            return stadiumRepository.save(foundStadium);
        } else {
            throw new EntityNotFoundException("Stadium not found with id: " + stadiumId);
        }
    }

    public void deleteStadium(UUID stadiumId) {
        if (!stadiumRepository.existsById(stadiumId)) {
            throw new EntityNotFoundException("League with id " + stadiumId + " not found");
        }
        stadiumRepository.deleteById(stadiumId);
    }
}
