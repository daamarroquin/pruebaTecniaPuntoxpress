package com.puntoxpress.backend.model;

import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.model.Enties.Reservation;
import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.model.Enum.ReservationStatus;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
class ReservationTest {

    private Validator validator;

    @BeforeEach
    void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    void whenAllFieldsAreValid_thenNoViolations() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User()); // Assuming User class is correctly defined
        reservation.setClient(new Client()); // Assuming Client class is correctly defined
        reservation.setLocation(new Location()); // Assuming Location class is correctly defined
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertTrue(violations.isEmpty());
    }

    @Test
    void whenUserIsNull_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("El usuario no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void whenClientIsNull_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("El cliente no puede ser nulo", violations.iterator().next().getMessage());
    }

    @Test
    void whenNameIsTooShort_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("A"); // Too short
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("El nombre de la reserva debe tener entre 2 y 100 caracteres", violations.iterator().next().getMessage());
    }

    @Test
    void whenDateIsNull_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("La fecha no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void whenTimeIsNull_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setNumberOfPeople(10);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("La hora no puede ser nula", violations.iterator().next().getMessage());
    }

    @Test
    void whenNumberOfPeopleIsLessThanOne_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(0); // Invalid value
        reservation.setStatus(ReservationStatus.CONFIRMED);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("El número de personas debe ser al menos 1", violations.iterator().next().getMessage());
    }

    @Test
    void whenStatusIsNull_thenViolation() {
        Reservation reservation = new Reservation();
        reservation.setUser(new User());
        reservation.setClient(new Client());
        reservation.setLocation(new Location());
        reservation.setName("Birthday Party");
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.now());
        reservation.setNumberOfPeople(10);

        Set<ConstraintViolation<Reservation>> violations = validator.validate(reservation);
        assertEquals(1, violations.size());
        assertEquals("El estado de la reserva no puede ser nulo", violations.iterator().next().getMessage());
    }
}