package com.puntoxpress.backend.repositories;

import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.model.Enum.Role;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.ANY)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User testUser;

    @BeforeEach
    void setUp() {
        // Crear un usuario de prueba
        testUser = new User();
        testUser.setId(UUID.randomUUID());
        testUser.setName("Test User");
        testUser.setEmail("testuser@example.com");
        testUser.setPassword("password123");
        testUser.setPhoneNumber("12345678");
        testUser.setRole(Role.CUSTOMER);

        // Guardar el usuario de prueba en la base de datos
        userRepository.save(testUser);
    }

    @Test
    public void testFindByEmail() {
        // Buscar el usuario por correo electrónico
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");

        // Verificar que el usuario se encontró y que los datos son correctos
        assertTrue(foundUser.isPresent());
        assertEquals(testUser.getEmail(), foundUser.get().getEmail());
        assertEquals(testUser.getName(), foundUser.get().getName());
    }

    @Test
    public void testFindByEmail_NotFound() {
        // Intentar encontrar un usuario que no existe
        Optional<User> foundUser = userRepository.findByEmail("nonexistent@example.com");

        // Verificar que no se encontró ningún usuario
        assertTrue(foundUser.isEmpty());
    }

    @Test
    public void updateInfo() {
        // Buscar el usuario por correo electrónico
        Optional<User> foundUser = userRepository.findByEmail("testuser@example.com");
        User user = foundUser.get();
        user.setName("Test User Updated");
        User UserUpade = userRepository.save(user);
        assertEquals(user.getName(), UserUpade.getName());
    }
}
