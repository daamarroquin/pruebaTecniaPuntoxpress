package com.puntoxpress.backend.model.Enties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
@EqualsAndHashCode(callSuper = true)
@Entity
public class Location extends BaseEntity  {

    @NotNull(message = "El número de la ubicación no puede ser nulo")
    @Size(min = 1, max = 50, message = "El número de la ubicación debe tener entre 1 y 50 caracteres")
    @Column(nullable = false, unique = true)
    private String number;

    @Min(value = 1, message = "La capacidad debe ser al menos 1")
    @Column(nullable = false)
    private int capacity;

    @NotNull(message = "El nombre de la ubicación no puede ser nulo")
    @Size(min = 2, max = 100, message = "El nombre de la ubicación debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String locationName;
}
