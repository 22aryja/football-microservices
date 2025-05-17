package org.example.seasonservice.services;

import com.example.football.models.SeasonCountry;
import com.example.football.repository.SeasonCountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonCountryService {

    private final SeasonCountryRepository seasonCountryRepository;

    public SeasonCountryService(SeasonCountryRepository seasonCountryRepository) {
        this.seasonCountryRepository = seasonCountryRepository;
    }

    public List<SeasonCountry> getHistorySnapshots() {
        return seasonCountryRepository.findAll();
    }

    public List<SeasonCountry> getSnapshotByYear(String from, String to) {
        return seasonCountryRepository.getSnapshotBySeason(from, to);
    }
}
