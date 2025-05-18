package org.example.countryservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.countryservice.client.CoachClient;
import org.example.countryservice.client.SeasonClient;
import org.example.countryservice.models.Country;
import org.example.countryservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CountryService {
    private final CountryRepository countryRepository;
    private final CoachClient coachClient;
    private final SeasonClient seasonClient;

    public CountryService(CountryRepository countryRepository, CoachClient coachClient, SeasonClient seasonClient) {
        this.countryRepository = countryRepository;
        this.coachClient = coachClient;
        this.seasonClient = seasonClient;
    }

    public List<Country> getCountries() {
        List<Country> countries = countryRepository.findAllCountriesWithLeagues();
        countries.forEach(this::enrichCountryWithExternalData);
        return countries;
    }

    public Country getCountryById(UUID id) {
        Country country = countryRepository.findCountryWithLeaguesById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
        enrichCountryWithExternalData(country);
        return country;
    }

    public Country createCountry(String name, Integer worldCups) {
        Optional<Country> possibleCountry = countryRepository.findCountryByName(name);
        possibleCountry.ifPresent(foundCountry -> {
            throw new IllegalStateException("Such country already exists");
        });

        Country newCountry = new Country();
        newCountry.setName(name);
        newCountry.setWorldCups(worldCups);

        return countryRepository.save(newCountry);
    }

    public Country updateCountry(UUID countryId, String name, Integer worldCups) {
        Optional<Country> possibleCountry = countryRepository.findById(countryId);

        if (possibleCountry.isPresent()) {
            Country foundCountry = possibleCountry.get();

            foundCountry.setName(name);
            foundCountry.setWorldCups(worldCups);

            return countryRepository.save(foundCountry);
        } else {
            throw new EntityNotFoundException("Country not found with id: " + countryId);
        }
    }

    public void deleteCountry(UUID countryId) {
        if (!countryRepository.existsById(countryId)) {
            throw new EntityNotFoundException("Country with id " + countryId + " not found");
        }
        countryRepository.deleteById(countryId);
    }

    private void enrichCountryWithExternalData(Country country) {
        try {
            country.setCoaches(coachClient.getCoachesByCountry(country.getId()));
            country.setSeasonCountries(seasonClient.getSeasonCountriesByCountry(country.getId()));
        } catch (Exception e) {
            // Log error but don't fail the request
            System.err.println("Error enriching country data: " + e.getMessage());
        }
    }
}

