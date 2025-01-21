package com.puntoxpress.backend.model;

import com.puntoxpress.backend.model.Enties.Location;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class LocationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        Location location = new Location();
        location.setNumber("A1");
        location.setCapacity(10);
        location.setLocationName("Main Hall");

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenNumberIsNull_thenViolation() {
        Location location = new Location();
        location.setCapacity(10);
        location.setLocationName("Main Hall");

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(1, violations.size());
        assertEquals("El número de la ubicación no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void whenNumberIsTooLong_thenViolation() {
        Location location = new Location();
        location.setNumber("A".repeat(51)); // Exceeding the limit
        location.setCapacity(10);
        location.setLocationName("Main Hall");

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(1, violations.size());
        assertEquals("El número de la ubicación debe tener entre 1 y 50 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenCapacityIsLessThanOne_thenViolation() {
        Location location = new Location();
        location.setNumber("A1");
        location.setCapacity(0); // Below minimum value
        location.setLocationName("Main Hall");

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(1, violations.size());
        assertEquals("La capacidad debe ser al menos 1", violations.iterator().next().getMessage());
    }

    @Test
    void whenLocationNameIsNull_thenViolation() {
        Location location = new Location();
        location.setNumber("A1");
        location.setCapacity(10);

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(1, violations.size());
        assertEquals("El nombre de la ubicación no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void whenLocationNameIsTooShort_thenViolation() {
        Location location = new Location();
        location.setNumber("A1");
        location.setCapacity(10);
        location.setLocationName("A"); // Below minimum length

        Set<ConstraintViolation<Location>> violations = validator.validate(location);
        assertEquals(1, violations.size());
        assertEquals("El nombre de la ubicación debe tener entre 2 y 100 caracteres", violations.iterator().next().getMessage());
    }
}
