package org.example.seasonservice.client;

import org.example.seasonservice.dto.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "country-service", url = "http://localhost:8081")
public interface CountryClient {
    
    @GetMapping("/api/v1/country/{countryId}")
    CountryDto getCountryById(@PathVariable UUID countryId);
} 