package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.Reservation;
import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.repositories.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ReservationService {
    private final ReservationRepository reservationRepository;

    @Autowired
    public ReservationService(ReservationRepository reservationRepository) {
        this.reservationRepository = reservationRepository;
    }

    public Reservation crear(Reservation infoRequest) {
        return reservationRepository.save(infoRequest);
    }

    public Page<Reservation> obtenerLista(Pageable pageable) {
        Pageable sortedByDate = PageRequest.of(
                pageable.getPageNumber(),
                pageable.getPageSize(),
                Sort.by(Sort.Direction.DESC, "date") // Orden ascendente por fecha
        );
        return reservationRepository.findAll(sortedByDate);
    }

    public Reservation actualizar(UUID id, Reservation infoRequest) {
        if (reservationRepository.existsById(id)) {
            infoRequest.setId(id);
            return reservationRepository.save(infoRequest);
        }
        return null;
    }

    public boolean eliminar(UUID id) {
        if (reservationRepository.existsById(id)) {
            reservationRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Reservation buscarPorId(UUID id) {
        return reservationRepository.findById(id).orElse(null);
    }
}
