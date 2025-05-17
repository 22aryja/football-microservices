package org.example.teamservice.models;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
@Table(name = "team")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false)
    private Integer amountOfPlayers;

    @ManyToOne
    @JoinColumn(name = "league_id", nullable = false)
    @JsonBackReference
    private League league;

    @OneToMany(mappedBy = "team")
    @JsonManagedReference
    private List<Player> players;

    @OneToOne
    @JoinColumn(name = "coach_id", referencedColumnName = "id")
    @JsonManagedReference
    private Coach coach;

    @OneToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id")
    @JsonManagedReference
    private Stadium stadium;

}
