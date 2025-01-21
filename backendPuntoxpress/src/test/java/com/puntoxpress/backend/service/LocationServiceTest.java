package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.repositories.LocationRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LocationServiceTest {

    @Mock
    private LocationRepository locationRepository;

    @InjectMocks
    private LocationService locationService;

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
    void crear_shouldSaveLocation() {
        when(locationRepository.save(location)).thenReturn(location);
        Location result = locationService.crear(location);
        assertNotNull(result);
        assertEquals(location, result);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void obtenerLista_shouldReturnLocationsPage() {
        Page<Location> locationPage = new PageImpl<>(Arrays.asList(location));
        when(locationRepository.findAll(any(Pageable.class))).thenReturn(locationPage);

        Page<Location> result = locationService.obtenerLista(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(location, result.getContent().get(0));
        verify(locationRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void actualizar_shouldUpdateLocationIfExists() {
        when(locationRepository.existsById(locationId)).thenReturn(true);
        when(locationRepository.save(location)).thenReturn(location);

        Location result = locationService.actualizar(locationId, location);

        assertNotNull(result);
        assertEquals(location, result);
        verify(locationRepository, times(1)).existsById(locationId);
        verify(locationRepository, times(1)).save(location);
    }

    @Test
    void actualizar_shouldReturnNullIfLocationNotExists() {
        when(locationRepository.existsById(locationId)).thenReturn(false);

        Location result = locationService.actualizar(locationId, location);

        assertNull(result);
        verify(locationRepository, times(1)).existsById(locationId);
        verify(locationRepository, times(0)).save(location);
    }

    @Test
    void eliminar_shouldDeleteLocationIfExists() {
        when(locationRepository.existsById(locationId)).thenReturn(true);

        boolean result = locationService.eliminar(locationId);

        assertTrue(result);
        verify(locationRepository, times(1)).existsById(locationId);
        verify(locationRepository, times(1)).deleteById(locationId);
    }

    @Test
    void eliminar_shouldReturnFalseIfLocationNotExists() {
        when(locationRepository.existsById(locationId)).thenReturn(false);

        boolean result = locationService.eliminar(locationId);

        assertFalse(result);
        verify(locationRepository, times(1)).existsById(locationId);
        verify(locationRepository, times(0)).deleteById(locationId);
    }

    @Test
    void buscarPorId_shouldReturnLocationIfExists() {
        when(locationRepository.findById(locationId)).thenReturn(Optional.of(location));

        Location result = locationService.buscarPorId(locationId);

        assertNotNull(result);
        assertEquals(location, result);
        verify(locationRepository, times(1)).findById(locationId);
    }

    @Test
    void buscarPorId_shouldReturnNullIfLocationNotExists() {
        when(locationRepository.findById(locationId)).thenReturn(Optional.empty());

        Location result = locationService.buscarPorId(locationId);

        assertNull(result);
        verify(locationRepository, times(1)).findById(locationId);
    }
}
