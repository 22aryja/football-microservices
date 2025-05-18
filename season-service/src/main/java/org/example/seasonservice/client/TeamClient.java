package org.example.seasonservice.client;

import org.example.seasonservice.dto.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import java.util.UUID;

@FeignClient(name = "team-service", url = "http://localhost:8084")
public interface TeamClient {
    
    @GetMapping("/api/v1/team/{teamId}")
    TeamDto getTeamById(@PathVariable UUID teamId);
} 