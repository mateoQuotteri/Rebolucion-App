package com.rebolucion.app.Servicio;


import com.rebolucion.app.Dtos.Entrada.UsuarioEntradaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioServicio {
    private final Logger LOGGER = LoggerFactory.getLogger(UsuarioServicio.class);
    private final UsuarioRepositorio usuarioRepository;

    private final ModelMapper modelMapper;

    public UsuarioSalidaDto modificarUsuario(UsuarioEntradaDto usuarioEntradaDto, Long id) throws RecursoNoEncontradoExcepcion {

        Usuario usuarioIngresado = modelMapper.map(usuarioEntradaDto, Usuario.class);
        Usuario usuarioActualizar = usuarioRepository.findById(id).orElse(null);

        UsuarioSalidaDto usuarioSalidaDto;

        if (usuarioActualizar != null){
            // Solo actualiza los campos que no son nulos en usuarioEntradaDto
            if (usuarioEntradaDto.getCorreo() != null) {
                usuarioActualizar.setCorreo(usuarioEntradaDto.getCorreo());
            }
            if (usuarioEntradaDto.getEdad() != null) {
                usuarioActualizar.setEdad(usuarioEntradaDto.getEdad());
            }
            if (usuarioEntradaDto.getApellido() != null) {
                usuarioActualizar.setApellido(usuarioEntradaDto.getApellido());
            }
            if (usuarioEntradaDto.getPais() != null) {
                usuarioActualizar.setPais(usuarioEntradaDto.getPais());
            }
            if (usuarioEntradaDto.getGenero() != null) {
                usuarioActualizar.setGenero(usuarioEntradaDto.getGenero());
            }
            if (usuarioEntradaDto.getNombre() != null) {
                usuarioActualizar.setNombre(usuarioEntradaDto.getNombre());
            }


            // Log antes de guardar
            LOGGER.info("Actualizando usuario con ID: " + id + " con los nuevos datos: " + usuarioActualizar);

            usuarioRepository.save(usuarioActualizar);

            // Log después de guardar
            LOGGER.info("Usuario con ID: " + id + " actualizado exitosamente.");

            usuarioSalidaDto = modelMapper.map(usuarioActualizar, UsuarioSalidaDto.class);
        } else {
            LOGGER.error("No fue posible actualizar los datos del usuario ya que no se encuentra en la base de datos");
            throw new RecursoNoEncontradoExcepcion("No es posible actualizar el usuario con ID: " + id + "ya que no se encuentra en la base de datos");
        }

        return usuarioSalidaDto;
    }
}
