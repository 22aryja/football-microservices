package org.example.seasonservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class TeamDto {
    private UUID id;
    private String name;
    private Integer amountOfPlayers;
    private UUID leagueId;
} 