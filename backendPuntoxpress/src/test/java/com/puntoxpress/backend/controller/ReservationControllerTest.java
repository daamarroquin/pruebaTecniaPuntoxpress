package com.puntoxpress.backend.controller;

import com.puntoxpress.backend.model.Enties.Reservation;
import com.puntoxpress.backend.model.dto.ResponseDTO;
import com.puntoxpress.backend.service.JwtService;
import com.puntoxpress.backend.service.ReservationService;
import com.puntoxpress.backend.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ReservationControllerTest {

        @Mock
        private ReservationService reservationService;

        @Mock
        private JwtService jwtService;

        @Mock
        private UserService userService;

        @Mock
        private HttpServletRequest request;

        @InjectMocks
        private ReservationController reservationController;

        private Reservation reservation;
        private UUID reservationId;

        @BeforeEach
        void setUp() {
            MockitoAnnotations.openMocks(this);
            reservationId = UUID.randomUUID();
            reservation = new Reservation();
            reservation.setId(reservationId);
            reservation.setName("Test Reservation");

            when(request.getHeader("Authorization")).thenReturn("Bearer test-token");
            when(jwtService.extractUsername("test-token")).thenReturn("test@example.com");
        }

        @Test
        void crear_shouldReturnCreatedResponse() {
            when(userService.buscarPorEmail("test@example.com")).thenReturn(null);
            when(reservationService.crear(reservation)).thenReturn(reservation);

            ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.crear(request, reservation);

            assertEquals(201, responseEntity.getStatusCodeValue());
            ResponseDTO<?> response = responseEntity.getBody();
            assertNotNull(response);
            assertEquals("Registro creado exitosamente", response.getMessage());
            verify(reservationService, times(1)).crear(reservation);
        }




    @Test
    void actualizar_shouldReturnUpdatedResponseIfExists() {
        when(reservationService.actualizar(reservationId, reservation)).thenReturn(reservation);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.actualizar(reservationId, reservation);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro actualizado exitosamente", response.getMessage());
        verify(reservationService, times(1)).actualizar(reservationId, reservation);
    }

    @Test
    void actualizar_shouldReturnNotFoundResponseIfNotExists() {
        when(reservationService.actualizar(reservationId, reservation)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.actualizar(reservationId, reservation);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(reservationService, times(1)).actualizar(reservationId, reservation);
    }

    @Test
    void eliminar_shouldReturnDeletedResponseIfExists() {
        when(reservationService.eliminar(reservationId)).thenReturn(true);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.eliminar(reservationId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro eliminado exitosamente", response.getMessage());
        verify(reservationService, times(1)).eliminar(reservationId);
    }

    @Test
    void eliminar_shouldReturnNotFoundResponseIfNotExists() {
        when(reservationService.eliminar(reservationId)).thenReturn(false);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.eliminar(reservationId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(reservationService, times(1)).eliminar(reservationId);
    }

    @Test
    void listar_shouldReturnListOfReservations() {
        List<Reservation> reservations = Arrays.asList(reservation);
        Page<Reservation> reservationPage = new PageImpl<>(reservations);
        when(reservationService.obtenerLista(any(Pageable.class))).thenReturn(reservationPage);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.listar(0, 100);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Listado de registros", response.getMessage());
        assertNotNull(response.getData());
        verify(reservationService, times(1)).obtenerLista(any(Pageable.class));
    }

    @Test
    void buscarPorId_shouldReturnReservationIfExists() {
        when(reservationService.buscarPorId(reservationId)).thenReturn(reservation);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.buscarPorId(reservationId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro encontrado", response.getMessage());
        verify(reservationService, times(1)).buscarPorId(reservationId);
    }

    @Test
    void buscarPorId_shouldReturnNotFoundIfNotExists() {
        when(reservationService.buscarPorId(reservationId)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.buscarPorId(reservationId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(reservationService, times(1)).buscarPorId(reservationId);
    }

    @Test
    void crear_shouldHandleException() {
        when(userService.buscarPorEmail("test@example.com")).thenReturn(null);
        when(reservationService.crear(reservation)).thenThrow(new RuntimeException("Error creating reservation"));

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.crear(request, reservation);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Ocurrio un error al crear", response.getMessage());
        verify(reservationService, times(1)).crear(reservation);
    }

    @Test
    void actualizar_shouldHandleException() {
        when(reservationService.actualizar(reservationId, reservation)).thenThrow(new RuntimeException("Error updating reservation"));

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.actualizar(reservationId, reservation);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al actualizar", response.getMessage());
        verify(reservationService, times(1)).actualizar(reservationId, reservation);
    }

    @Test
    void eliminar_shouldHandleException() {
        when(reservationService.eliminar(reservationId)).thenThrow(new RuntimeException("Error deleting reservation"));

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.eliminar(reservationId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al eliminar", response.getMessage());
        verify(reservationService, times(1)).eliminar(reservationId);
    }

    @Test
    void listar_shouldHandleException() {
        when(reservationService.obtenerLista(any())).thenThrow(new RuntimeException("Error listing reservations"));

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.listar(0, 100);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al listar", response.getMessage());
        verify(reservationService, times(1)).obtenerLista(any());
    }

    @Test
    void buscarPorId_shouldHandleException() {
        when(reservationService.buscarPorId(reservationId)).thenThrow(new RuntimeException("Error finding reservation"));

        ResponseEntity<ResponseDTO<?>> responseEntity = reservationController.buscarPorId(reservationId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al buscar el registro", response.getMessage());
        verify(reservationService, times(1)).buscarPorId(reservationId);
    }
}
