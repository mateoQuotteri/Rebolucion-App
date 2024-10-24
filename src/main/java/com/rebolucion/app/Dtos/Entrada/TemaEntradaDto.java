package com.rebolucion.app.Dtos.Entrada;


import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaEntradaDto {
    @NotNull
    private String nombre;
    @NotNull

    private String icono;
    @NotNull

    private Boolean hecho;
}
