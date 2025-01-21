package com.puntoxpress.backend.model.Enties;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.puntoxpress.backend.model.Enum.Role;
import jakarta.persistence.*;
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
@Table(name = "Userinfo")
public class User extends BaseEntity{

    @NotNull(message = "El nombre no puede ser nulo")
    @Size(min = 2, max = 100, message = "El nombre debe tener entre 2 y 100 caracteres")
    @Column(nullable = false)
    private String name;

    @NotNull(message = "El correo electrónico no puede ser nulo")
    @Email(message = "El correo electrónico debe ser válido")
    @Size(max = 100, message = "El correo electrónico no puede tener más de 100 caracteres")
    @Column(nullable = false, unique = true)
    private String email;

    @NotNull(message = "La contraseña no puede ser nula")
    @Size(min = 8, message = "La contraseña debe tener al menos 8 caracteres")
    @Column(nullable = false)
    private String password;

    @NotNull(message = "El número de teléfono no puede ser nulo")
    @Pattern(regexp = "^[0-9]{8}$", message = "El número de teléfono debe ser válido y tener 8 dígitos")
    @Column(nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "El rol no puede ser nulo")
    @Column(nullable = false)
    private Role role;
}
