package com.puntoxpress.backend.repositories;

import com.puntoxpress.backend.model.Enties.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;
@Repository
public interface LocationRepository extends JpaRepository<Location, UUID> {
}
