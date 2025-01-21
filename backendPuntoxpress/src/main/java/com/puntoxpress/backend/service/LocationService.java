package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.repositories.LocationRepository;
import com.puntoxpress.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class LocationService {

    private final LocationRepository locationRepository;

    @Autowired
    public LocationService(LocationRepository locationRepository) {
        this.locationRepository = locationRepository;
    }

    public Location crear(Location infoRequest) {
        return locationRepository.save(infoRequest);
    }

    public Page<Location> obtenerLista(Pageable pageable) {
        return locationRepository.findAll(pageable);
    }

    public Location actualizar(UUID id, Location infoRequest) {
        if (locationRepository.existsById(id)) {
            infoRequest.setId(id);
            return locationRepository.save(infoRequest);
        }
        return null;
    }

    public boolean eliminar(UUID id) {
        if (locationRepository.existsById(id)) {
            locationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Location buscarPorId(UUID id) {
        return locationRepository.findById(id).orElse(null);
    }
}
