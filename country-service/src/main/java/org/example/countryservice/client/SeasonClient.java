package org.example.countryservice.client;

import org.example.countryservice.dto.SeasonCountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "season-service", url = "http://localhost:8083")
public interface SeasonClient {
    
    @GetMapping("/api/v1/season/by-country/{countryId}")
    List<SeasonCountryDto> getSeasonCountriesByCountry(@PathVariable UUID countryId);
    
    @GetMapping("/api/v1/season/{seasonCountryId}")
    SeasonCountryDto getSeasonCountryById(@PathVariable UUID seasonCountryId);
} 