package org.example.seasonservice.controllers;

import org.example.seasonservice.models.SeasonCountry;
import org.example.seasonservice.services.SeasonCountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(path = "api/v1/season/history")
public class SeasonCountryController {

    private final SeasonCountryService seasonCountryService;

    @Autowired
    public SeasonCountryController(SeasonCountryService seasonCountryService) {
        this.seasonCountryService = seasonCountryService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<SeasonCountry>> getHistorySnapshots(){
        List<SeasonCountry> seasonCountries = seasonCountryService.getHistorySnapshots();
        return ResponseEntity.ok(seasonCountries);
    }

    @GetMapping("/season")
    public ResponseEntity<List<SeasonCountry>> getSnapshotByYear(@RequestParam String from, @RequestParam String to){
        List<SeasonCountry> seasonCountries = seasonCountryService.getSnapshotByYear(from, to);
        return ResponseEntity.ok(seasonCountries);
    }
}
