package com.rebolucion.app.Auth.Request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {
     @NotNull(message = "El nombre no puede ser nulo.")
     @NotBlank(message = "Debe agregarse un nombre.")
     @Size(min = 2, max = 20, message = "El nombre ingresado debe tener entre 2 y 20 caracteres.")
     @Pattern(regexp = "^[A-Za-z]{2,20}$", message = "El nombre debe tener entre 2 y 20 letras y solo puede contener caracteres alfabéticos.")
     @Valid
     private String nombre;

     @NotNull(message = "El apellido no puede ser nulo.")
     @NotBlank(message = "Debe agregarse un apellido.")
     @Size(min = 2, max = 20, message = "El apellido ingresado debe tener entre 2 y 20 caracteres.")
     @Pattern(regexp = "^[A-Za-z]{2,20}$", message = "El nombre debe tener entre 3 y 20 letras y solo puede contener caracteres alfabéticos.")
     @Valid
     private String apellido;

     @NotNull(message = "El correo no puede ser nulo.")
     @NotBlank(message = "Debe agregarse un correo.")
     @Email(message = "El correo debe ser una dirección de correo electrónico válida.")
     private String correo;

     @NotNull
     @Pattern(
             regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,25}$",
             message = "La contraseña debe tener entre 8 y 25 caracteres, incluyendo al menos una letra mayúscula, una letra minúscula, un número y un carácter especial."
     )
     private String contra;
}
