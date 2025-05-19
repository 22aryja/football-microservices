package org.example.teamservice.client;

import org.example.teamservice.dto.LeagueDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "league-service", url = "http://localhost:8081")
public interface LeagueClient {
    
    @GetMapping("/api/v1/country/league/{leagueId}")
    LeagueDto getLeagueById(@PathVariable UUID leagueId);
} 