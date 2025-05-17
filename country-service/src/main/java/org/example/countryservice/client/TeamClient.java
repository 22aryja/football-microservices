package org.example.countryservice.client;

import org.example.countryservice.dto.TeamDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import java.util.List;
import java.util.UUID;

@FeignClient(name = "team-service", url = "http://localhost:8084")
public interface TeamClient {

    @GetMapping("/api/v1/team/by-league")
    List<TeamDto> getTeamsByLeague(@RequestParam UUID leagueId);
}
