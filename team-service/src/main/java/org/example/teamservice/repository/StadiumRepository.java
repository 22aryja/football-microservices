package org.example.teamservice.repository;
import org.example.teamservice.models.Stadium;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface StadiumRepository extends JpaRepository<Stadium, UUID> {
}
