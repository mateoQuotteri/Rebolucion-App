package com.rebolucion.app.Dtos.Entrada;

import com.rebolucion.app.Entidades.Tema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class ModuloEntradaDto {

    @NotNull
    private String nombre;
    @NotNull
    private Integer dificultad;
    @NotNull

    private String profesor;
    @NotNull

    private String descripcion;

    @NotNull
    private Long temaId;

}
