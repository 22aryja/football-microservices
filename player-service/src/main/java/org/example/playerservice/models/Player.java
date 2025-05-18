package org.example.playerservice.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.example.playerservice.dto.TeamDto;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "player")
public class Player {

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
    private String tShirtNumber;

    @Column(nullable = false)
    private UUID teamId;

    @Transient
    private TeamDto team;

    @OneToOne
    @JoinColumn(name = "player_stats_id", referencedColumnName = "id")
    @JsonManagedReference
    private PlayerStats playerStats;
}
