package org.example.teamservice.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.teamservice.dto.LeagueDto;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer amountOfPlayers;

    @Column(nullable = false)
    private UUID leagueId;

    @Transient
    private LeagueDto league;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    @JsonManagedReference
    private Coach coach;

    @OneToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @JsonManagedReference
    private Stadium stadium;
}
