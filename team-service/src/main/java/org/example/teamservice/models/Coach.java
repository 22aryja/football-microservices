package org.example.teamservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "coach")
public class Coach extends Person {

    @ManyToOne
    @JoinColumn(name = "country_id", nullable = false)
    @JsonBackReference
    private Country countryFrom;

    @OneToOne(mappedBy = "coach")
    @JsonBackReference
    private Team team;
}
