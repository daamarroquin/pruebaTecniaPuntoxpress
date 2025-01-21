package com.puntoxpress.backend.controller;

import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.model.dto.ResponseDTO;
import com.puntoxpress.backend.service.UserService;
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

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private User user;
    private UUID userId;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        userId = UUID.randomUUID();
        user = new User();
        user.setId(userId);
        user.setName("Test User");
    }

    @Test
    void crear_shouldReturnCreatedResponse() {
        when(userService.crear(user)).thenReturn(user);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.crear(user);

        assertEquals(201, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro creado exitosamente", response.getMessage());
        verify(userService, times(1)).crear(user);
    }

    @Test
    void actualizar_shouldReturnUpdatedResponseIfExists() {
        when(userService.actualizar(userId, user)).thenReturn(user);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.actualizar(userId, user);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro actualizado exitosamente", response.getMessage());
        verify(userService, times(1)).actualizar(userId, user);
    }

    @Test
    void actualizar_shouldReturnNotFoundResponseIfNotExists() {
        when(userService.actualizar(userId, user)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.actualizar(userId, user);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(userService, times(1)).actualizar(userId, user);
    }

    @Test
    void eliminar_shouldReturnDeletedResponseIfExists() {
        when(userService.eliminar(userId)).thenReturn(true);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.eliminar(userId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro eliminado exitosamente", response.getMessage());
        verify(userService, times(1)).eliminar(userId);
    }

    @Test
    void eliminar_shouldReturnNotFoundResponseIfNotExists() {
        when(userService.eliminar(userId)).thenReturn(false);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.eliminar(userId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(userService, times(1)).eliminar(userId);
    }

    @Test
    void listar_shouldReturnListOfUsers() {
        List<User> users = Arrays.asList(user);
        Page<User> userPage = new PageImpl<>(users);
        when(userService.obtenerLista(any(Pageable.class))).thenReturn(userPage);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.listar(0, 100);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Listado de registros", response.getMessage());
        assertNotNull(response.getData());
        verify(userService, times(1)).obtenerLista(any(Pageable.class));
    }

    @Test
    void buscarPorId_shouldReturnUserIfExists() {
        when(userService.buscarPorId(userId)).thenReturn(user);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.buscarPorId(userId);

        assertEquals(200, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro encontrado", response.getMessage());
        verify(userService, times(1)).buscarPorId(userId);
    }

    @Test
    void buscarPorId_shouldReturnNotFoundIfNotExists() {
        when(userService.buscarPorId(userId)).thenReturn(null);

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.buscarPorId(userId);

        assertEquals(404, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertNotNull(response);
        assertEquals("Registro no encontrado", response.getMessage());
        verify(userService, times(1)).buscarPorId(userId);
    }

    @Test
    void crear_shouldHandleException() {
        when(userService.crear(user)).thenThrow(new RuntimeException("Error creating user"));

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.crear(user);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrio un error al crear", response.getMessage());
        verify(userService, times(1)).crear(user);
    }

    @Test
    void actualizar_shouldHandleException() {
        when(userService.actualizar(userId, user)).thenThrow(new RuntimeException("Error updating user"));

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.actualizar(userId, user);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al actualizar", response.getMessage());
        verify(userService, times(1)).actualizar(userId, user);
    }

    @Test
    void eliminar_shouldHandleException() {
        when(userService.eliminar(userId)).thenThrow(new RuntimeException("Error deleting user"));

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.eliminar(userId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al eliminar", response.getMessage());
        verify(userService, times(1)).eliminar(userId);
    }

    @Test
    void listar_shouldHandleException() {
        when(userService.obtenerLista(any())).thenThrow(new RuntimeException("Error listing users"));

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.listar(0, 100);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al listar", response.getMessage());
        verify(userService, times(1)).obtenerLista(any());
    }

    @Test
    void buscarPorId_shouldHandleException() {
        when(userService.buscarPorId(userId)).thenThrow(new RuntimeException("Error finding user"));

        ResponseEntity<ResponseDTO<?>> responseEntity = userController.buscarPorId(userId);

        assertEquals(500, responseEntity.getStatusCodeValue());
        ResponseDTO<?> response = responseEntity.getBody();
        assertEquals("Ocurrió un error al buscar el registro", response.getMessage());
        verify(userService, times(1)).buscarPorId(userId);
    }
}
