package com.rebolucion.app.Controladores;


import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Servicio.AdminServicio;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServicio adminService;

    @GetMapping("/usuarios")
    public ResponseEntity<List<UsuarioSalidaDto>> listarUsuarios(){
        return new ResponseEntity<>(adminService.listarUsuarios(), HttpStatus.OK);
    }


    @GetMapping("/usuario/{id}")
    public ResponseEntity<UsuarioSalidaDto> buscarUsuarioPorId(@PathVariable Long id){
        return new ResponseEntity<>(adminService.buscarUsuarioPorId(id), HttpStatus.OK);
    }


    @DeleteMapping("/eliminar-usuario/{id}")
    public ResponseEntity<?> eliminarUsuarioPorId(@PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        adminService.eliminarUsuario(id);
        return new ResponseEntity<>("Usuario eliminado correctamente: ", HttpStatus.NO_CONTENT);
    }
}
