package org.example.teamservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.teamservice.dto.CountryDto;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "coach")
public class Coach {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private Date birthdate;

    @Column(nullable = false)
    private Integer salary;

    @Column(nullable = false)
    private UUID countryFromId;

    @Transient
    private CountryDto countryFrom;

    @OneToOne(mappedBy = "coach")
    @JsonBackReference
    private Team team;
}
