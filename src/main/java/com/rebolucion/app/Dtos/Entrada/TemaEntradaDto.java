package com.rebolucion.app.Dtos.Entrada;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemaEntradaDto {
    private String nombre;
    private String icono;
    private Boolean hecho;
}
