package com.rebolucion.app.Dtos.Salida;

import com.rebolucion.app.Entidades.Rol;
import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioSalidaDto {

    private long id;
    private String nombre;
    private String apellido;
    private String correo;
    private Rol rol;
    private String pais;
    private Integer edad;
    private String genero;

}
