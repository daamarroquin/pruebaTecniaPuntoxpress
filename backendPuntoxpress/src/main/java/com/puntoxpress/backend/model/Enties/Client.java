package com.puntoxpress.backend.model.Enties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
public class Client extends BaseEntity {

    @NotNull(message = "El nombre del cliente no puede ser nulo")
    @Size(min = 2, max = 100, message = "El nombre del cliente debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String firstName;

    @NotNull(message = "El apellido del cliente no puede ser nulo")
    @Size(min = 2, max = 100, message = "El apellido del cliente debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String lastName;

    @NotNull(message = "El correo del cliente no puede ser nulo")
    @Email(message = "Debe proporcionar un correo electrónico válido")
    @Size(min = 5, max = 100, message = "El correo del cliente debe tener entre 5 y 100 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "El número de teléfono no puede ser nulo")
    @Pattern(regexp = "^[0-9]{8}$", message = "El número de teléfono debe ser válido y tener 8 dígitos")
    @Column(nullable = false)
    private String phoneNumber;
}