package org.example.playerservice.client;

import org.example.playerservice.dto.TeamDto;
import org.example.playerservice.models.Player;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "team-service", url = "http://localhost:8084")
public interface TeamClient {
    
    @GetMapping("/api/v1/team/{teamId}")
    TeamDto getTeamById(@PathVariable UUID teamId);
} 