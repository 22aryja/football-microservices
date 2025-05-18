package org.example.countryservice.client;

import org.example.countryservice.dto.CoachDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "player-service", url = "http://localhost:8082")
public interface CoachClient {
    
    @GetMapping("/api/v1/coach/by-country/{countryId}")
    List<CoachDto> getCoachesByCountry(@PathVariable UUID countryId);
    
    @GetMapping("/api/v1/coach/{coachId}")
    CoachDto getCoachById(@PathVariable UUID coachId);
} 