package com.rebolucion.app.Controladores;


import com.rebolucion.app.Auth.AuthResponse;
import com.rebolucion.app.Dtos.Entrada.ModuloEntradaDto;
import com.rebolucion.app.Dtos.Entrada.TemaEntradaDto;
import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import com.rebolucion.app.Dtos.Salida.ModuloSalidaDto;
import com.rebolucion.app.Dtos.Salida.TemaSalidaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Modulo;
import com.rebolucion.app.Entidades.Tema;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Servicio.ModuloServicio;
import com.rebolucion.app.Servicio.TemaServicio;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.rebolucion.app.Servicio.AdminServicio;

import java.util.List;

@RestController
//@CrossOrigin(origins = "http://localhost:5173")
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final AdminServicio adminService;
    private final TemaServicio temaServicio;
    private final ModuloServicio moduloServicio;



    // ENDPOINTS REFERIDOS A USUARIOS

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







    // ENDPOINTS REFERIDOS A TEMAS
    @PostMapping("/agregar-tema")
    public ResponseEntity<TemaSalidaDto> agregarTema(@Valid @RequestBody TemaEntradaDto request){
        return new ResponseEntity(temaServicio.agregarTema(request), HttpStatus.OK);
    }

    @GetMapping("/temas")
    public ResponseEntity<List<TemaSalidaDto>> listarTemas(){
        return new ResponseEntity<>(temaServicio.listarTemas(), HttpStatus.OK);
    }
    @GetMapping("/tema/{id}")
    public ResponseEntity<TemaSalidaDto> buscarTemaPorId(@PathVariable Long id){
        return new ResponseEntity<>(temaServicio.buscarTemaPorId(id), HttpStatus.OK);
    }
    @DeleteMapping("/eliminar-tema/{id}")
    public ResponseEntity<?> eliminarTemaPorId(@PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        temaServicio.eliminarTemaPorId(id);
        return new ResponseEntity<>("Tema eliminado correctamente: ", HttpStatus.NO_CONTENT);
    }

    @PutMapping("/modificar-tema/{id}")
    public ResponseEntity<TemaSalidaDto> modificarTema(@RequestBody TemaEntradaDto tema, @PathVariable Long id) throws RecursoNoEncontradoExcepcion {
        return new ResponseEntity<>(temaServicio.modificarTema(tema, id), HttpStatus.OK);
    }


    // ENDPOINTS REFERIDOS A MODULOS

    @PostMapping("/agregar-modulo")
    public ResponseEntity<ModuloSalidaDto> agregarModulo(@Valid @RequestBody ModuloEntradaDto request) throws RecursoNoEncontradoExcepcion {
        return new ResponseEntity(moduloServicio.agregarModulo(request), HttpStatus.OK);
    }

  @GetMapping("/modulos")
    public ResponseEntity<List<ModuloSalidaDto>> listarModulos(){
        return new ResponseEntity<>(moduloServicio.listarModulos(), HttpStatus.OK);
    }

    @GetMapping("/modulo/{id}")
    public ResponseEntity<ModuloSalidaDto> buscarModuloporId(@PathVariable Long id){
        return new ResponseEntity<>(moduloServicio.buscarModuloPorId(id), HttpStatus.OK);
    }

    // ENDPOINTS REFERIDOS A UNIDADES



}
