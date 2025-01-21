package com.puntoxpress.backend.model;

import com.puntoxpress.backend.model.Enties.Client;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
public class ClientTest {
    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testValidClient() {
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setPhoneNumber("12345678");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertTrue(violations.isEmpty(), "Client should be valid");
    }

    @Test
    public void testInvalidClientFirstName() {
        Client client = new Client();
        client.setFirstName(null); // Invalid
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setPhoneNumber("76873872");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("El nombre del cliente no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidClientEmail() {
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("invalid-email"); // Invalid email
        client.setPhoneNumber("76873872");

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("Debe proporcionar un correo electrónico válido", violations.iterator().next().getMessage());
    }

    @Test
    public void testInvalidClientPhoneNumber() {
        Client client = new Client();
        client.setFirstName("John");
        client.setLastName("Doe");
        client.setEmail("john.doe@example.com");
        client.setPhoneNumber("7687382"); // Invalid phone number

        Set<ConstraintViolation<Client>> violations = validator.validate(client);
        assertEquals(1, violations.size());
        assertEquals("El número de teléfono debe ser válido y tener 8 dígitos", violations.iterator().next().getMessage());
    }
}
