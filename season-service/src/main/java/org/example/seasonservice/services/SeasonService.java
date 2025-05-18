package org.example.seasonservice.services;

import org.example.seasonservice.models.Season;
import jakarta.persistence.EntityNotFoundException;
import org.example.seasonservice.repositories.SeasonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class SeasonService {

    private final SeasonRepository seasonRepository;

    public SeasonService(SeasonRepository seasonRepository) {
        this.seasonRepository = seasonRepository;
    }

    public List<Season> getSeasons(){
        return seasonRepository.findAll();
    }

    @Transactional
    public Season createSeason(String from, String to){
        boolean exists = seasonRepository.findSeasonByYears(from, to).isPresent();
        if (exists) {
            throw new IllegalStateException("Season already exists from " + from + " to " + to);
        }

        Season season = new Season();
        season.setFromYear(from);
        season.setToYear(to);

        return seasonRepository.save(season);
    }

    public void deleteSeason(String from, String to){
        Season season = seasonRepository.findSeasonByYears(from , to)
                .orElseThrow(() -> new EntityNotFoundException("Season from the years " + from + " - " + to + " does not exist"));

        seasonRepository.delete(season);
    }
}
