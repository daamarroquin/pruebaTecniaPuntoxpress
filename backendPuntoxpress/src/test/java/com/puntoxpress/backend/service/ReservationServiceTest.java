package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.Reservation;
import com.puntoxpress.backend.repositories.ReservationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationServiceTest {

    @Mock
    private ReservationRepository reservationRepository;

    @InjectMocks
    private ReservationService reservationService;

    private Reservation reservation;
    private UUID reservationId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        reservationId = UUID.randomUUID();
        reservation = new Reservation();
        reservation.setId(reservationId);
        reservation.setName("Test Reservation");
    }

    @Test
    void crear_shouldSaveReservation() {
        when(reservationRepository.save(reservation)).thenReturn(reservation);
        Reservation result = reservationService.crear(reservation);
        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void obtenerLista_shouldReturnReservationsPage() {
        Page<Reservation> reservationPage = new PageImpl<>(Arrays.asList(reservation));
        Pageable pageable = PageRequest.of(0, 10); // Página 0, 10 elementos por página

        when(reservationRepository.findAll(any(Pageable.class))).thenReturn(reservationPage);

        Page<Reservation> result = reservationService.obtenerLista(pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(reservation, result.getContent().get(0));
        verify(reservationRepository, times(1)).findAll(any(Pageable.class));
    }


    @Test
    void actualizar_shouldUpdateReservationIfExists() {
        when(reservationRepository.existsById(reservationId)).thenReturn(true);
        when(reservationRepository.save(reservation)).thenReturn(reservation);

        Reservation result = reservationService.actualizar(reservationId, reservation);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(1)).save(reservation);
    }

    @Test
    void actualizar_shouldReturnNullIfReservationNotExists() {
        when(reservationRepository.existsById(reservationId)).thenReturn(false);

        Reservation result = reservationService.actualizar(reservationId, reservation);

        assertNull(result);
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(0)).save(reservation);
    }

    @Test
    void eliminar_shouldDeleteReservationIfExists() {
        when(reservationRepository.existsById(reservationId)).thenReturn(true);

        boolean result = reservationService.eliminar(reservationId);

        assertTrue(result);
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(1)).deleteById(reservationId);
    }

    @Test
    void eliminar_shouldReturnFalseIfReservationNotExists() {
        when(reservationRepository.existsById(reservationId)).thenReturn(false);

        boolean result = reservationService.eliminar(reservationId);

        assertFalse(result);
        verify(reservationRepository, times(1)).existsById(reservationId);
        verify(reservationRepository, times(0)).deleteById(reservationId);
    }

    @Test
    void buscarPorId_shouldReturnReservationIfExists() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.of(reservation));

        Reservation result = reservationService.buscarPorId(reservationId);

        assertNotNull(result);
        assertEquals(reservation, result);
        verify(reservationRepository, times(1)).findById(reservationId);
    }

    @Test
    void buscarPorId_shouldReturnNullIfReservationNotExists() {
        when(reservationRepository.findById(reservationId)).thenReturn(Optional.empty());

        Reservation result = reservationService.buscarPorId(reservationId);

        assertNull(result);
        verify(reservationRepository, times(1)).findById(reservationId);
    }
}
