package com.rebolucion.app.Servicio;

import com.rebolucion.app.Dtos.Entrada.ModuloEntradaDto;
import com.rebolucion.app.Dtos.Salida.ModuloSalidaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Utiles.JsonPrinter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import com.rebolucion.app.Repositorio.ModuloRepositorio;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServicio {
    private final Logger LOGGER = LoggerFactory.getLogger(AdminServicio.class);


    private final UsuarioRepositorio usuarioRepository;
    private final ModuloRepositorio moduloRepositorio;
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
            } else LOGGER.error("Usuario no encontrado: {}", JsonPrinter.toString(usuarioEncontrado));
            return usuarioEncontrado;
        }catch (Exception e){
            LOGGER.info("Error: {}", e);

        }

        return usuarioEncontrado;
    }

    public void eliminarUsuario(Long id) throws RecursoNoEncontradoExcepcion {
        if (buscarUsuarioPorId(id) != null){
            usuarioRepository.deleteById(id);
            LOGGER.warn("Se ha eliminado el usuario con id: {}", + id);
        } else throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun usuario con el ID: " + id);
    }








}
