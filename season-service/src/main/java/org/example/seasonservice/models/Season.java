package org.example.seasonservice.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "season")
public class Season {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String fromYear;

    @Column(nullable = false)
    private String toYear;

    @OneToMany(mappedBy = "season")
    @JsonManagedReference
    private List<SeasonCountry> seasonCountries;

 }
