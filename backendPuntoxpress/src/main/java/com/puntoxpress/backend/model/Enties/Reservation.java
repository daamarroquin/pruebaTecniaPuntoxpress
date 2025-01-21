package com.puntoxpress.backend.model.Enties;

import com.puntoxpress.backend.model.Enum.ReservationStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
public class Reservation extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @NotNull(message = "El usuario no puede ser nulo")
    private User user;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    @NotNull(message = "El cliente no puede ser nulo")
    private Client client;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @NotNull(message = "El nombre de la reserva no puede ser nulo")
    @Size(min = 2, max = 100, message = "El nombre de la reserva debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "La fecha no puede ser nula")
    @Column(nullable = false)
    private LocalDate date;

    @NotNull(message = "La hora no puede ser nula")
    @Column(nullable = false)
    private LocalTime time;

    @Min(value = 1, message = "El número de personas debe ser al menos 1")
    @Column(nullable = false)
    private int numberOfPeople;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El estado de la reserva no puede ser nulo")
    @Column(nullable = false)
    private ReservationStatus status;
}
