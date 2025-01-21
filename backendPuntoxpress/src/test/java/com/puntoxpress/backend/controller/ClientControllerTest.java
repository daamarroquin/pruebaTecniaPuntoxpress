package com.puntoxpress.backend.controller;

import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.model.dto.ResponseDTO;
import com.puntoxpress.backend.service.ClientService;
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

class ClientControllerTest {

    @Mock
    private ClientService clientService;

    @InjectMocks
    private ClientController clientController;

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
    void crear_shouldReturnCreatedResponse() {
        when(clientService.crear(client)).thenReturn(client);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.crear(client);

        assertEquals(201, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro creado exitosamente", response.getMessage());
        verify(clientService, times(1)).crear(client);
    }

    @Test
    void actualizar_shouldReturnUpdatedResponseIfExists() {
        when(clientService.actualizar(clientId, client)).thenReturn(client);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.actualizar(clientId, client);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro actualizado exitosamente", response.getMessage());
        verify(clientService, times(1)).actualizar(clientId, client);
    }

    @Test
    void actualizar_shouldReturnNotFoundResponseIfNotExists() {
        when(clientService.actualizar(clientId, client)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.actualizar(clientId, client);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(clientService, times(1)).actualizar(clientId, client);
    }

    @Test
    void eliminar_shouldReturnDeletedResponseIfExists() {
        when(clientService.eliminar(clientId)).thenReturn(true);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.eliminar(clientId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro eliminado exitosamente", response.getMessage());
        verify(clientService, times(1)).eliminar(clientId);
    }

    @Test
    void eliminar_shouldReturnNotFoundResponseIfNotExists() {
        when(clientService.eliminar(clientId)).thenReturn(false);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.eliminar(clientId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(clientService, times(1)).eliminar(clientId);
    }

    @Test
    void listar_shouldReturnListOfClients() {
        List<Client> clients = Arrays.asList(client);
        Page<Client> clientPage = new PageImpl<>(clients);
        when(clientService.obtenerLista(any(Pageable.class))).thenReturn(clientPage);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.listar(0, 100);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Listado de registros", response.getMessage());
        assertNotNull(response.getData());
        verify(clientService, times(1)).obtenerLista(any(Pageable.class));
    }

    @Test
    void buscarPorId_shouldReturnClientIfExists() {
        when(clientService.buscarPorId(clientId)).thenReturn(client);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.buscarPorId(clientId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro encontrado", response.getMessage());
        verify(clientService, times(1)).buscarPorId(clientId);
    }

    @Test
    void buscarPorId_shouldReturnNotFoundIfNotExists() {
        when(clientService.buscarPorId(clientId)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.buscarPorId(clientId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(clientService, times(1)).buscarPorId(clientId);
    }

    @Test
    void crear_shouldHandleException() {
        when(clientService.crear(client)).thenThrow(new RuntimeException("Error creating client"));

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.crear(client);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrio un error al crear", response.getMessage());
        verify(clientService, times(1)).crear(client);
    }

    @Test
    void actualizar_shouldHandleException() {
        when(clientService.actualizar(clientId, client)).thenThrow(new RuntimeException("Error updating client"));

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.actualizar(clientId, client);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al actualizar", response.getMessage());
        verify(clientService, times(1)).actualizar(clientId, client);
    }

    @Test
    void eliminar_shouldHandleException() {
        when(clientService.eliminar(clientId)).thenThrow(new RuntimeException("Error deleting client"));

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.eliminar(clientId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al eliminar", response.getMessage());
        verify(clientService, times(1)).eliminar(clientId);
    }

    @Test
    void listar_shouldHandleException() {
        when(clientService.obtenerLista(any())).thenThrow(new RuntimeException("Error listing clients"));

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.listar(0, 100);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al listar", response.getMessage());
        verify(clientService, times(1)).obtenerLista(any());
    }

    @Test
    void buscarPorId_shouldHandleException() {
        when(clientService.buscarPorId(clientId)).thenThrow(new RuntimeException("Error finding client"));

        ResponseEntity<ResponseDTO<?>> responseEntity = clientController.buscarPorId(clientId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al buscar el registro", response.getMessage());
        verify(clientService, times(1)).buscarPorId(clientId);
    }
}
