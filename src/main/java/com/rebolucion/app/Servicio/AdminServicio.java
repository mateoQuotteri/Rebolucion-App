package com.rebolucion.app.Servicio;

import com.rebolucion.app.Dtos.Entrada.ModuloEntradaDto;
import com.rebolucion.app.Dtos.Entrada.TemaEntradaDto;
import com.rebolucion.app.Dtos.Salida.ModuloSalidaDto;
import com.rebolucion.app.Dtos.Salida.TemaSalidaDto;
import com.rebolucion.app.Dtos.Salida.UsuarioSalidaDto;
import com.rebolucion.app.Entidades.Modulo;
import com.rebolucion.app.Entidades.Tema;
import com.rebolucion.app.Entidades.Usuario;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Repositorio.ModuloRepositorio;
import com.rebolucion.app.Repositorio.TemaRepositorio;
import com.rebolucion.app.Repositorio.UsuarioRepositorio;
import com.rebolucion.app.Utiles.JsonPrinter;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class AdminServicio {
    private final Logger LOGGER = LoggerFactory.getLogger(AuthServicio.class);


    private final UsuarioRepositorio usuarioRepository;
    private final TemaRepositorio temaRepositorio;
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

    public TemaSalidaDto buscarTemaPorId(Long id) {
        Tema temaBuscado = temaRepositorio.findById(id).orElse(null);
        TemaSalidaDto temaEncontrado = null;

        try{
            if (temaBuscado != null){
                temaEncontrado = modelMapper.map(temaBuscado, TemaSalidaDto.class);
                LOGGER.info("Tema encontrado: {}", JsonPrinter.toString(temaEncontrado));
            } else LOGGER.error("Tema no encontrado: {}", JsonPrinter.toString(temaBuscado));
            return temaEncontrado;
        }catch (Exception e){
            LOGGER.info("Error: {}", e);

        }

        return temaEncontrado;
    }


    public void eliminarTemaPorId(Long id) throws RecursoNoEncontradoExcepcion{
        if (buscarTemaPorId(id) != null){
            temaRepositorio.deleteById(id);
            LOGGER.warn("Se ha eliminado el tema con id: {}", + id);
        } else throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun usuario con el ID: " + id);
    }



    public TemaSalidaDto modificarTema(TemaEntradaDto temaEntradaDto, Long id) throws RecursoNoEncontradoExcepcion {

        Tema temaIngresado = modelMapper.map(temaEntradaDto, Tema.class);
        Tema temaActualizar = temaRepositorio.findById(id).orElse(null);

        TemaSalidaDto temaSalidaDto;

        if (temaActualizar != null){
            // Solo actualiza los campos que no son nulos en usuarioEntradaDto
            if (temaEntradaDto.getHecho() != null) {
                temaActualizar.setHecho(temaEntradaDto.getHecho());
            }
            if (temaEntradaDto.getIcono() != null) {
                temaActualizar.setIcono(temaEntradaDto.getIcono());
            }
            if (temaEntradaDto.getNombre() != null) {
                temaActualizar.setNombre(temaEntradaDto.getNombre());
            }


            // Log antes de guardar
            LOGGER.info("Actualizando tema con ID: " + id + " con los nuevos datos: " + temaActualizar);

            temaRepositorio.save(temaActualizar);

            // Log después de guardar
            LOGGER.info("Usuario con ID: " + id + " actualizado exitosamente.");

            temaSalidaDto = modelMapper.map(temaActualizar, TemaSalidaDto.class);
        } else {
            LOGGER.error("No fue posible actualizar los datos del tema ya que no se encuentra en la base de datos");
            throw new RecursoNoEncontradoExcepcion("No es posible actualizar el tema con ID: " + id + "ya que no se encuentra en la base de datos");
        }

        return temaSalidaDto;
    }

    // METODOS MOULOS
    public ResponseEntity<ModuloSalidaDto> agregarModulo(@Valid ModuloEntradaDto request)  throws  RecursoNoEncontradoExcepcion {

        Tema temaCorrespondiente = temaRepositorio.findById(request.getTemaId()).orElse(null);
        
        ModuloSalidaDto moduloParaDevolver = null;
        if (temaCorrespondiente != null) {
            Modulo modulo = Modulo.builder()
                    .nombre(request.getNombre())
                    .descripcion(request.getDescripcion())
                    .dificultad(request.getDificultad())
                    .tema(temaCorrespondiente)
                    .profesor(request.getProfesor())
                    .build();

            Modulo moduloGuardado = moduloRepositorio.save(modulo);

            TemaSalidaDto temaSalidaDto = modelMapper.map(temaCorrespondiente, TemaSalidaDto.class);

            // Convertir el Tema guardado en un TemaSalidaDto
            ModuloSalidaDto moduloSalidaDto = ModuloSalidaDto.builder()
                    .nombre(moduloGuardado.getNombre())
                    .descripcion(moduloGuardado.getDescripcion())
                    .dificultad(moduloGuardado.getDificultad())
                    .profesor(moduloGuardado.getProfesor())
                    .temaSalidaDto(temaSalidaDto)
                    .build();

           moduloParaDevolver = moduloSalidaDto;
        }

        return ResponseEntity.ok(moduloParaDevolver);
    }

    public List<ModuloSalidaDto> listarModulos() {

        List<ModuloSalidaDto> moduloSalidaDtos = moduloRepositorio.findAll()
                .stream()
                .map(modulo -> {
                    ModuloSalidaDto dto = modelMapper.map(modulo, ModuloSalidaDto.class);
                    if (modulo.getTema() != null) {
                        TemaSalidaDto temaSalidaDto = modelMapper.map(modulo.getTema(), TemaSalidaDto.class);
                        dto.setTemaSalidaDto(temaSalidaDto);
                    }
                    return dto;
                })
                .toList();
        return moduloSalidaDtos;
    }}


    //METODOS UNIDADES

