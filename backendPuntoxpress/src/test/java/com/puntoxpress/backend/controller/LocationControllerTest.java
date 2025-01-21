package com.puntoxpress.backend.controller;

import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.model.dto.ResponseDTO;
import com.puntoxpress.backend.service.LocationService;
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
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationControllerTest {

    @Mock
    private LocationService locationService;

    @InjectMocks
    private LocationController locationController;

    private Location location;
    private UUID locationId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        locationId = UUID.randomUUID();
        location = new Location();
        location.setId(locationId);
        location.setLocationName("Test Location");
    }

    @Test
    void crear_shouldReturnCreatedResponse() {
        when(locationService.crear(location)).thenReturn(location);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.crear(location);

        assertEquals(201, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro creado exitosamente", response.getMessage());
        verify(locationService, times(1)).crear(location);
    }

    @Test
    void actualizar_shouldReturnUpdatedResponseIfExists() {
        when(locationService.actualizar(locationId, location)).thenReturn(location);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.actualizar(locationId, location);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro actualizado exitosamente", response.getMessage());
        verify(locationService, times(1)).actualizar(locationId, location);
    }

    @Test
    void actualizar_shouldReturnNotFoundResponseIfNotExists() {
        when(locationService.actualizar(locationId, location)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.actualizar(locationId, location);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(locationService, times(1)).actualizar(locationId, location);
    }

    @Test
    void eliminar_shouldReturnDeletedResponseIfExists() {
        when(locationService.eliminar(locationId)).thenReturn(true);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.eliminar(locationId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro eliminado exitosamente", response.getMessage());
        verify(locationService, times(1)).eliminar(locationId);
    }

    @Test
    void eliminar_shouldReturnNotFoundResponseIfNotExists() {
        when(locationService.eliminar(locationId)).thenReturn(false);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.eliminar(locationId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(locationService, times(1)).eliminar(locationId);
    }

    @Test
    void listar_shouldReturnListOfLocations() {
        List<Location> locations = Arrays.asList(location);
        Page<Location> locationPage = new PageImpl<>(locations);
        when(locationService.obtenerLista(any(Pageable.class))).thenReturn(locationPage);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.listar(0, 100);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Listado de registros", response.getMessage());
        assertNotNull(response.getData());
        verify(locationService, times(1)).obtenerLista(any(Pageable.class));
    }

    @Test
    void buscarPorId_shouldReturnLocationIfExists() {
        when(locationService.buscarPorId(locationId)).thenReturn(location);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.buscarPorId(locationId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro encontrado", response.getMessage());
        verify(locationService, times(1)).buscarPorId(locationId);
    }

    @Test
    void buscarPorId_shouldReturnNotFoundIfNotExists() {
        when(locationService.buscarPorId(locationId)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.buscarPorId(locationId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(locationService, times(1)).buscarPorId(locationId);
    }
    @Test
    void crear_shouldHandleException() {
        when(locationService.crear(location)).thenThrow(new RuntimeException("Error creating location"));

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.crear(location);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrio un error al crear", response.getMessage());
        verify(locationService, times(1)).crear(location);
    }

    @Test
    void actualizar_shouldHandleException() {
        when(locationService.actualizar(locationId, location)).thenThrow(new RuntimeException("Error updating location"));

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.actualizar(locationId, location);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al actualizar", response.getMessage());
        verify(locationService, times(1)).actualizar(locationId, location);
    }

    @Test
    void eliminar_shouldHandleException() {
        when(locationService.eliminar(locationId)).thenThrow(new RuntimeException("Error deleting location"));

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.eliminar(locationId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al eliminar", response.getMessage());
        verify(locationService, times(1)).eliminar(locationId);
    }

    @Test
    void listar_shouldHandleException() {
        when(locationService.obtenerLista(any())).thenThrow(new RuntimeException("Error listing locations"));

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.listar(0, 100);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al listar", response.getMessage());
        verify(locationService, times(1)).obtenerLista(any());
    }

    @Test
    void buscarPorId_shouldHandleException() {
        when(locationService.buscarPorId(locationId)).thenThrow(new RuntimeException("Error finding location"));

        ResponseEntity<ResponseDTO<?>> responseEntity = locationController.buscarPorId(locationId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al buscar el registro", response.getMessage());
        verify(locationService, times(1)).buscarPorId(locationId);
    }
}
