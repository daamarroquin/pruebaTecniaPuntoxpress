package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.repositories.ClientRepository;
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

class ClientServiceTest {

    @Mock
    private ClientRepository clientRepository;

    @InjectMocks
    private ClientService clientService;

    private Client client;
    private UUID clientId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        clientId = UUID.randomUUID();
        client = new Client();
        client.setId(clientId);
        client.setFirstName("Test Client");
    }

    @Test
    void crear_shouldSaveClient() {
        when(clientRepository.save(client)).thenReturn(client);
        Client result = clientService.crear(client);
        assertNotNull(result);
        assertEquals(client, result);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void obtenerLista_shouldReturnClientsPage() {
        Page<Client> clientPage = new PageImpl<>(Arrays.asList(client));
        when(clientRepository.findAll(any(Pageable.class))).thenReturn(clientPage);

        Page<Client> result = clientService.obtenerLista(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(client, result.getContent().get(0));
        verify(clientRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void actualizar_shouldUpdateClientIfExists() {
        when(clientRepository.existsById(clientId)).thenReturn(true);
        when(clientRepository.save(client)).thenReturn(client);

        Client result = clientService.actualizar(clientId, client);

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(1)).save(client);
    }

    @Test
    void actualizar_shouldReturnNullIfClientNotExists() {
        when(clientRepository.existsById(clientId)).thenReturn(false);

        Client result = clientService.actualizar(clientId, client);

        assertNull(result);
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(0)).save(client);
    }

    @Test
    void eliminar_shouldDeleteClientIfExists() {
        when(clientRepository.existsById(clientId)).thenReturn(true);

        boolean result = clientService.eliminar(clientId);

        assertTrue(result);
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(1)).deleteById(clientId);
    }

    @Test
    void eliminar_shouldReturnFalseIfClientNotExists() {
        when(clientRepository.existsById(clientId)).thenReturn(false);

        boolean result = clientService.eliminar(clientId);

        assertFalse(result);
        verify(clientRepository, times(1)).existsById(clientId);
        verify(clientRepository, times(0)).deleteById(clientId);
    }

    @Test
    void buscarPorId_shouldReturnClientIfExists() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.of(client));

        Client result = clientService.buscarPorId(clientId);

        assertNotNull(result);
        assertEquals(client, result);
        verify(clientRepository, times(1)).findById(clientId);
    }

    @Test
    void buscarPorId_shouldReturnNullIfClientNotExists() {
        when(clientRepository.findById(clientId)).thenReturn(Optional.empty());

        Client result = clientService.buscarPorId(clientId);

        assertNull(result);
        verify(clientRepository, times(1)).findById(clientId);
    }
}