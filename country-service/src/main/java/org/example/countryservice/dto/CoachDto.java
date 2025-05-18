package org.example.countryservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CoachDto {
    private UUID id;
    private String name;
    private String surname;
    private UUID countryFromId;
    private UUID teamId;
} 