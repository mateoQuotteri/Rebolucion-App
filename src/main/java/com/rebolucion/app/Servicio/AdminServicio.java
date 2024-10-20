package com.rebolucion.app.Servicio;

import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import com.rebolucion.app.Utiles.JsonPrinter;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminServicio {
    private final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(AuthServicio.class);


    private final UsuarioRepositorio usuarioRepository;
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
}
