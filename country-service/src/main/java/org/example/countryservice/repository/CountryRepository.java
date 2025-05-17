package org.example.countryservice.repository;

//import com.example.football.models.League;
import org.example.countryservice.models.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CountryRepository extends JpaRepository<Country, UUID> {

    @Query("SELECT name FROM Country WHERE name = :name")
    Optional<Country> findCountryByName(String name);

    @Query("SELECT c FROM Country c LEFT JOIN FETCH c.leagues WHERE c.id = :id")
    Optional<Country> findCountryWithLeaguesById(UUID id);

    @Query("SELECT DISTINCT c FROM Country c LEFT JOIN FETCH c.leagues")
    List<Country> findAllCountriesWithLeagues();

}
