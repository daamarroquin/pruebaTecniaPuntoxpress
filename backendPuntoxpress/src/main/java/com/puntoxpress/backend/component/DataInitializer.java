package com.puntoxpress.backend.component;

import com.puntoxpress.backend.model.Enties.Client;
import com.puntoxpress.backend.model.Enties.Location;
import com.puntoxpress.backend.model.Enties.Reservation;
import com.puntoxpress.backend.model.Enties.User;
import com.puntoxpress.backend.model.Enum.ReservationStatus;
import com.puntoxpress.backend.model.Enum.Role;
import com.puntoxpress.backend.repositories.ClientRepository;
import com.puntoxpress.backend.repositories.LocationRepository;
import com.puntoxpress.backend.repositories.ReservationRepository;
import com.puntoxpress.backend.repositories.UserRepository;
import com.puntoxpress.backend.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final UserService userService;
    private final UserRepository userRepository;
    private final ClientRepository clientRepository;
    private final ReservationRepository reservationRepository;
    private final LocationRepository locationRepository;

    public DataInitializer(UserService userService, UserRepository userRepository, ClientRepository clientRepository, ReservationRepository reservationRepository, LocationRepository locationRepository) {
        this.userService = userService;
        this.userRepository = userRepository;
        this.clientRepository = clientRepository;
        this.reservationRepository = reservationRepository;
        this.locationRepository = locationRepository;
    }

    @Override
    public void run(String... args) throws Exception {

        // Insertar un usuario
        User user = new User();
        user.setName("Admin");
        user.setEmail("admin@example.com");
        user.setPassword("password");
        user.setPhoneNumber("76873872");
        user.setRole(Role.ADMIN);

        if (userRepository.count() <= 0) {
            userService.crear(user);
        }

        // Insertar un cliente
        Client client = new Client();
        client.setFirstName("generic");
        client.setLastName("user");
        client.setEmail("userGeneric@example.com");
        client.setPhoneNumber("00000000");

        if (clientRepository.count() <= 0) {
            clientRepository.save(client);
        }

        // Insertar una locacion

        Location location = new Location();
        location.setNumber("0");
        location.setCapacity(1);
        location.setLocationName("Default");

        if (locationRepository.count() <= 0) {
            locationRepository.save(location);
        }

        // Insertar una reserva
        Reservation reservation = new Reservation();
        reservation.setUser(user);
        reservation.setClient(client);
        reservation.setDate(LocalDate.now());
        reservation.setTime(LocalTime.of(18, 30));
        reservation.setNumberOfPeople(4);
        reservation.setName("Prueba");
        reservation.setLocation(null);
        reservation.setStatus(ReservationStatus.CONFIRMED);

        if (reservationRepository.count() <= 0) {
            reservationRepository.save(reservation);
        }
    }
}