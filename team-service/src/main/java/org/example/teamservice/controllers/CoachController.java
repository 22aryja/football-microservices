package org.example.teamservice.controllers;

import com.example.football.dto.CoachDto;
import com.example.football.models.Coach;
import com.example.football.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping(path = "api/v1/coach")
public class CoachController {

    private final CoachService coachService;

    @Autowired
    public CoachController(CoachService coachService) {
        this.coachService = coachService;
    }

    @GetMapping
    public ResponseEntity<List<Coach>> getAllCoaches(){
        List<Coach> coaches = coachService.getAllCoaches();
        return ResponseEntity.ok(coaches);
    }

    @PostMapping
    public ResponseEntity<Coach> createCoach(@ModelAttribute CoachDto request) {
        Coach coach = coachService.createCoach(request);
        return ResponseEntity.ok(coach);
    }

    @DeleteMapping(path = "{coachId}")
    public ResponseEntity<Void> deleteCoach(@PathVariable UUID coachId){
        coachService.deleteCoach(coachId);
        return ResponseEntity.noContent().build();
    }
}
