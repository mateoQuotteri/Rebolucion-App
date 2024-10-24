package com.rebolucion.app.Servicio;


import com.rebolucion.app.Dtos.Entrada.ModuloEntradaDto;
import com.rebolucion.app.Dtos.Salida.ModuloSalidaDto;
import com.rebolucion.app.Dtos.Salida.TemaSalidaDto;
import com.rebolucion.app.Entidades.Modulo;
import com.rebolucion.app.Entidades.Tema;
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
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ModuloServicio {

    private final Logger LOGGER = LoggerFactory.getLogger(ModuloServicio.class);

    private final UsuarioRepositorio usuarioRepository;
    private final ModuloRepositorio moduloRepositorio;
    private final TemaRepositorio temaRepositorio;
    private final ModelMapper modelMapper;


// METODOS MOULOS
public ResponseEntity<ModuloSalidaDto> agregarModulo(@Valid ModuloEntradaDto request) throws RecursoNoEncontradoExcepcion {
    // Buscar el tema correspondiente por ID
    Tema temaCorrespondiente = temaRepositorio.findById(request.getTemaId()).orElse(null);
    ModuloSalidaDto moduloParaDevolver = null;

    if (temaCorrespondiente != null) {
        // Crear un nuevo objeto Modulo
        Modulo modulo = Modulo.builder()
                .nombre(request.getNombre())
                .descripcion(request.getDescripcion())
                .dificultad(request.getDificultad())
                .tema(temaCorrespondiente)
                .profesor(request.getProfesor())
                .build();

        // Guardar el módulo en la base de datos
        Modulo moduloGuardado = moduloRepositorio.save(modulo);

        // Convertir el Tema guardado en un TemaSalidaDto
        TemaSalidaDto temaSalidaDto = modelMapper.map(temaCorrespondiente, TemaSalidaDto.class);
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

    // Método para listar todos los módulos
    public List<ModuloSalidaDto> listarModulos() {
        List<ModuloSalidaDto> moduloSalidaDtos = moduloRepositorio.findAll()
                .stream()
                .map(modulo -> {
                    // Convertir cada módulo a ModuloSalidaDto
                    ModuloSalidaDto dto = modelMapper.map(modulo, ModuloSalidaDto.class);
                    if (modulo.getTema() != null) {
                        // Convertir el tema asociado a TemaSalidaDto
                        TemaSalidaDto temaSalidaDto = modelMapper.map(modulo.getTema(), TemaSalidaDto.class);
                        dto.setTemaSalidaDto(temaSalidaDto);
                    }
                    return dto;
                })
                .toList();
        return moduloSalidaDtos;
    }

    // Método para buscar un módulo por ID



    public ModuloSalidaDto buscarModuloPorId(Long id) {
        Modulo moduloBuscado = moduloRepositorio.findById(id).orElse(null);
        ModuloSalidaDto moduloEncontrado = null;

        try{
            if (moduloBuscado != null){
                moduloEncontrado = modelMapper.map(moduloBuscado, ModuloSalidaDto.class);
                LOGGER.info("Modulo encontrado: {}", JsonPrinter.toString(moduloEncontrado));
            } else LOGGER.error("Modulo no encontrado: {}", JsonPrinter.toString(moduloBuscado));
            return moduloEncontrado;
        }catch (Exception e){
            LOGGER.info("Error: {}", e);

        }

        return moduloEncontrado;
    }
}
