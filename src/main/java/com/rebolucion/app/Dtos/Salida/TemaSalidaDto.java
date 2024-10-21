package com.rebolucion.app.Dtos.Salida;

import jdk.jshell.Snippet;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemaSalidaDto {
    private Long id;
    private String nombre;
    private String icono;
    private Boolean hecho;


}
