package org.example.countryservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class SeasonCountryDto {
    private UUID id;
    private UUID countryId;
    private UUID seasonId;
    private Integer place;
    private Integer points;
} 