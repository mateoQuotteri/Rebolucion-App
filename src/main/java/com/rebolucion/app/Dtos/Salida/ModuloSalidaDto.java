package com.rebolucion.app.Dtos.Salida;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ModuloSalidaDto {

    private Integer id;
    private String nombre;
    private Integer dificultad;
    private String profesor;
    private String descripcion;
    private TemaSalidaDto temaSalidaDto;
}
