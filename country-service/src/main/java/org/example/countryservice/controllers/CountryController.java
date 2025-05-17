package org.example.countryservice.controllers;

import org.example.countryservice.models.Country;
import org.example.countryservice.services.CountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/country")
public class CountryController {

    private final CountryService countryService;

    @Autowired
    public CountryController(CountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping
    public ResponseEntity<List<Country>> getCountries() {
        List<Country> countries = countryService.getCountries();
        return ResponseEntity.ok(countries);
    }

    @GetMapping(path = "{countryId}")
    public ResponseEntity<Country> getCountry(@PathVariable UUID countryId) {
        Country country = countryService.getCountryById(countryId);
        return ResponseEntity.ok(country);
    }

    @PostMapping
    public ResponseEntity<Country> createCountry(@RequestParam String name, @RequestParam Integer worldCups) {
        Country createdCountry = countryService.createCountry(name, worldCups);
        return ResponseEntity.status(201).body(createdCountry);
    }

    @PatchMapping(path = "{countryId}")
    public ResponseEntity<Country> updateCountry(
            @PathVariable UUID countryId,
            @RequestParam String name,
            @RequestParam Integer worldCups
    ) {
        Country updatedCountry = countryService.updateCountry(countryId, name, worldCups);
        return ResponseEntity.ok(updatedCountry);
    }

    @DeleteMapping(path = "{countryId}")
    public ResponseEntity<Void> deleteCountry(@PathVariable UUID countryId) {
        countryService.deleteCountry(countryId);
        return ResponseEntity.noContent().build();
    }
}
