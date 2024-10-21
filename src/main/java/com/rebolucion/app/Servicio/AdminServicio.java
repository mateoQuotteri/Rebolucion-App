package com.rebolucion.app.Servicio;

import com.rebolucion.app.Auth.AuthResponse;
import com.rebolucion.app.Dtos.Entrada.TemaEntradaDto;
import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import com.rebolucion.app.Dtos.Salida.TemaSalidaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Rol;
import com.rebolucion.app.Entidades.Tema;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Repositorio.TemaRepositorio;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import com.rebolucion.app.Utiles.JsonPrinter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminServicio {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthServicio.class);


    private final UsuarioRepositorio usuarioRepository;
    private final TemaRepositorio temaRepositorio;
    private final ModelMapper modelMapper;

    public List<UsuarioSalidaDto> listarUsuarios() {

        List<UsuarioSalidaDto> usuarioSalidaDto = usuarioRepository.findAll()
                .stream()
                .map(usuario -> modelMapper.map(usuario, UsuarioSalidaDto.class)
                )
                .toList();

        LOGGER.info("Lista de usuarios registrados: {}", JsonPrinter.toString(usuarioSalidaDto));
        return usuarioSalidaDto;
    }

    public UsuarioSalidaDto buscarUsuarioPorId(Long id) {
        Usuario usuarioBuscado = usuarioRepository.findById(id).orElse(null);
        UsuarioSalidaDto usuarioEncontrado = null;

        try{
        if (usuarioBuscado != null){
            usuarioEncontrado = modelMapper.map(usuarioBuscado, UsuarioSalidaDto.class);
            LOGGER.info("Usuario encontrado: {}", JsonPrinter.toString(usuarioEncontrado));
        } else LOGGER.error("Usuario no encontrado: {}", JsonPrinter.toString(usuarioBuscado));
          return usuarioEncontrado;
    }catch (Exception e){
            LOGGER.info("Error: {}", e);

        }

        return usuarioEncontrado;
    }

    public void eliminarUsuario(Long id) throws RecursoNoEncontradoExcepcion{
        if (buscarUsuarioPorId(id) != null){
            usuarioRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el usuario con id: {}", + id);
        } else throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun usuario con el ID: " + id);
    }




    //METODOS TEMAS
    public ResponseEntity<TemaSalidaDto> agregarTema(@Valid TemaEntradaDto request) {

        Tema tema = Tema.builder()
                .icono(request.getIcono())
                .hecho(request.getHecho())
                .nombre(request.getNombre())
                .build();

        // Guardar el Tema en la base de datos
        Tema temaGuardado = temaRepositorio.save(tema);

        // Convertir el Tema guardado en un TemaSalidaDto
        TemaSalidaDto temaSalidaDto = TemaSalidaDto.builder()
                .icono(temaGuardado.getIcono())
                .hecho(temaGuardado.getHecho())
                .nombre(temaGuardado.getNombre())
                .build();

        // Retornar el TemaSalidaDto en la respuesta
        return ResponseEntity.ok(temaSalidaDto);

    }


    public List<TemaSalidaDto> listarTemas() {

        List<TemaSalidaDto> temaSalidaDtos = temaRepositorio.findAll()
                .stream()
                .map(tema -> modelMapper.map(tema, TemaSalidaDto.class)
                )
                .toList();

        LOGGER.info("Lista de temas registrados: {}", JsonPrinter.toString(temaSalidaDtos));
        return temaSalidaDtos;
    }


    // METODOS MOULOS

    //METODOS UNIDADES
}
