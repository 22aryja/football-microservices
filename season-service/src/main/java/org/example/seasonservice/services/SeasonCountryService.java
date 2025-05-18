package org.example.seasonservice.services;

import org.example.seasonservice.client.CountryClient;
import org.example.seasonservice.dto.CountryDto;
import org.example.seasonservice.models.SeasonCountry;
import org.example.seasonservice.repository.SeasonCountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SeasonCountryService {

    private final SeasonCountryRepository seasonCountryRepository;
    private final CountryClient countryClient;

    public SeasonCountryService(SeasonCountryRepository seasonCountryRepository, CountryClient countryClient) {
        this.seasonCountryRepository = seasonCountryRepository;
        this.countryClient = countryClient;
    }

    public List<SeasonCountry> getHistorySnapshots() {
        List<SeasonCountry> snapshots = seasonCountryRepository.findAll();
        snapshots.forEach(this::enrichSeasonCountryWithCountryData);
        return snapshots;
    }

    public List<SeasonCountry> getSnapshotByYear(String from, String to) {
        List<SeasonCountry> snapshots = seasonCountryRepository.getSnapshotBySeason(from, to);
        snapshots.forEach(this::enrichSeasonCountryWithCountryData);
        return snapshots;
    }

    private void enrichSeasonCountryWithCountryData(SeasonCountry seasonCountry) {
        try {
            CountryDto country = countryClient.getCountryById(seasonCountry.getCountryId());
            seasonCountry.setCountry(country);
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Error enriching season country data: " + e.getMessage());
        }
    }
}
