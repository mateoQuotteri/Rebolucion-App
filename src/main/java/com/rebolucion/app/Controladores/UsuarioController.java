package com.rebolucion.app.Controladores;


import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Servicio.UsuarioServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/")
//@CrossOrigin(origins = "http://localhost:5173")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioServicio usuarioService;

    @PutMapping("/modificar-usuario/{id}")
    public ResponseEntity<UsuarioSalidaDto> modificarUsuario(@RequestBody UsuarioEntradaDto usuario, @PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        return new ResponseEntity<>(usuarioService.modificarUsuario(usuario, id), HttpStatus.OK);
    }
}
