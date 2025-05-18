package org.example.teamservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class LeagueDto {
    private UUID id;
    private String name;
    private Integer amountOfTeams;
    private UUID countryId;
} 