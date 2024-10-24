package com.rebolucion.app.Servicio;

import com.rebolucion.app.Dtos.Entrada.TemaEntradaDto;
import com.rebolucion.app.Dtos.Salida.TemaSalidaDto;
import com.rebolucion.app.Entidades.Tema;
import com.rebolucion.app.Excepciones.RecursoNoEncontradoExcepcion;
import com.rebolucion.app.Repositorio.TemaRepositorio;
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

public class TemaServicio {

    private final TemaRepositorio temaRepositorio;
    private final ModelMapper modelMapper;
    private final Logger LOGGER = LoggerFactory.getLogger(TemaServicio.class);

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


    public void eliminarTemaPorId(Long id) throws RecursoNoEncontradoExcepcion {
        if (buscarTemaPorId(id) != null){
            temaRepositorio.deleteById(id);
            LOGGER.warn("Se ha eliminado el tema con id: {}", + id);
        } else throw new RecursoNoEncontradoExcepcion("No se ha encontrado ningun tema con el ID: " + id);
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

}
