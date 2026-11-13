package com.acti4.crudrapido.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AlumnoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @Min(value = 1, message = "La edad debe ser mayor a cero")
    private int edad;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "El correo no tiene un formato válido")
    private String email;
}