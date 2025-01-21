package com.puntoxpress.backend.service;

import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Mock
    private PasswordEncoder passwordEncoder;

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
    void crear_shouldSaveUser() {
        when(passwordEncoder.encode("plaintextpassword")).thenReturn("hashedpassword");

        when(userRepository.save(user)).thenReturn(user);
        User result = userService.crear(user);
        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void obtenerLista_shouldReturnUsersPage() {
        Page<User> userPage = new PageImpl<>(Arrays.asList(user));
        when(userRepository.findAll(any(Pageable.class))).thenReturn(userPage);

        Page<User> result = userService.obtenerLista(Pageable.unpaged());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals(user, result.getContent().get(0));
        verify(userRepository, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void actualizar_shouldUpdateUserIfExists() {
        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User result = userService.actualizar(userId, user);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    void actualizar_shouldReturnNullIfUserNotExists() {
        when(userRepository.existsById(userId)).thenReturn(false);

        User result = userService.actualizar(userId, user);

        assertNull(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(0)).save(user);
    }

    @Test
    void eliminar_shouldDeleteUserIfExists() {
        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = userService.eliminar(userId);

        assertTrue(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void eliminar_shouldReturnFalseIfUserNotExists() {
        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = userService.eliminar(userId);

        assertFalse(result);
        verify(userRepository, times(1)).existsById(userId);
        verify(userRepository, times(0)).deleteById(userId);
    }

    @Test
    void buscarPorId_shouldReturnUserIfExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        User result = userService.buscarPorId(userId);

        assertNotNull(result);
        assertEquals(user, result);
        verify(userRepository, times(1)).findById(userId);
    }

    @Test
    void buscarPorId_shouldReturnNullIfUserNotExists() {
        when(userRepository.findById(userId)).thenReturn(Optional.empty());

        User result = userService.buscarPorId(userId);

        assertNull(result);
        verify(userRepository, times(1)).findById(userId);
    }
}
