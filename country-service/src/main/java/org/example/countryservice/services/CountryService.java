package org.example.countryservice.services;

import jakarta.persistence.EntityNotFoundException;
import org.example.countryservice.models.Country;
import org.example.countryservice.repository.CountryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class CountryService {
    private final CountryRepository countryRepository;

    public CountryService(CountryRepository countryRepository) {
        this.countryRepository = countryRepository;
    }

    public List<Country> getCountries() {
        return countryRepository.findAllCountriesWithLeagues();
    }

    public Country getCountryById(UUID id) {
        return countryRepository.findCountryWithLeaguesById(id)
                .orElseThrow(() -> new EntityNotFoundException("Country not found with id: " + id));
    }

    public Country createCountry(String name, Integer worldCups) {
        Optional<Country> possibleCountry =  countryRepository.findCountryByName(name);
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
}

