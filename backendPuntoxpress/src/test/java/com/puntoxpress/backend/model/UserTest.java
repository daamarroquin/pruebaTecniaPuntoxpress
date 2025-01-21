package com.puntoxpress.backend.model;

import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.model.Enum.Role;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("12345678");
        user.setRole(Role.ADMIN);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNameIsNull_thenViolation() {
        User user = new User();
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("12345678");
        user.setRole(Role.CUSTOMER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("El nombre no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void whenEmailIsInvalid_thenViolation() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("invalid-email");
        user.setPassword("password123");
        user.setPhoneNumber("12345678");
        user.setRole(Role.CUSTOMER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("El correo electrónico debe ser válido", violations.iterator().next().getMessage());
    }

    @Test
    void whenPasswordIsTooShort_thenViolation() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("short");
        user.setPhoneNumber("12345678");
        user.setRole(Role.CUSTOMER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("La contraseña debe tener al menos 8 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenPhoneNumberIsInvalid_thenViolation() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("invalid-phone"); // Invalid phone number
        user.setRole(Role.CUSTOMER);

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("El número de teléfono debe ser válido y tener 8 dígitos", violations.iterator().next().getMessage());
    }

    @Test
    void whenRoleIsNull_thenViolation() {
        User user = new User();
        user.setName("John Doe");
        user.setEmail("john.doe@example.com");
        user.setPassword("password123");
        user.setPhoneNumber("12345678");

        Set<ConstraintViolation<User>> violations = validator.validate(user);
        assertEquals(1, violations.size());
        assertEquals("El rol no puede ser nulo", violations.iterator().next().getMessage());
    }
}
