package org.example.seasonservice.controllers;
import com.example.football.models.Season;
import com.example.football.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/season")
public class SeasonController {

    private final SeasonService seasonService;

    @Autowired
    public SeasonController(SeasonService seasonService) {
        this.seasonService = seasonService;
    }

    @GetMapping
    public ResponseEntity<List<Season>> getAllSeasons(){
        List<Season> seasons = seasonService.getSeasons();
        return ResponseEntity.ok(seasons);
    }

    @PostMapping
    public ResponseEntity<Season> createSeason(@RequestParam String from, @RequestParam String to){
        Season season = seasonService.createSeason(from ,to);
        return ResponseEntity.ok(season);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteSeason(@RequestParam String from, @RequestParam String to){
        seasonService.deleteSeason(from, to);
        return ResponseEntity.noContent().build();
    }
}
