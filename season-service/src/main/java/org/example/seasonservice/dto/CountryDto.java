package org.example.seasonservice.dto;

import lombok.Data;
import java.util.UUID;

@Data
public class CountryDto {
    private UUID id;
    private String name;
    private Integer worldCups;
} 