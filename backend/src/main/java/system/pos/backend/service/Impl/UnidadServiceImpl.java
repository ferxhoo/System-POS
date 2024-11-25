package system.pos.backend.service.Impl;

import system.pos.backend.Repository.UnidadRepository;
import system.pos.backend.dto.Inventario.UnidadDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperUnidad;
import system.pos.backend.model.Unidad;
import system.pos.backend.service.Interfaces.UnidadService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnidadServiceImpl implements UnidadService {

    private static final String UNIDAD_NO_ENCONTRADA = "Unidad no encontrada con ID: ";
    private static final String UNIDAD_EXISTENTE = "Unidad con este nombre ya existe";

    @Autowired
    private UnidadRepository unidadRepository;

    @Override
    @Transactional
    public void inicializarUnidadesDefeault() {
        List<String> unidadesPorDefecto = List.of("Kg", "gr", "Lt", "Galón");
        unidadesPorDefecto.forEach(nombreUnidad -> 
            unidadRepository.findByNombreUnidadIgnoreCase(nombreUnidad)
                    .orElseGet(() -> unidadRepository.save(createUnidad(nombreUnidad)))
        );
    }

    private Unidad createUnidad(String nombreUnidad) {
        return Unidad.builder()
                .nombreUnidad(nombreUnidad)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public UnidadDTO buscarUnidadPorId(Long id) {
        Unidad unidad = unidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UNIDAD_NO_ENCONTRADA + id));
        return MapperUnidad.convertUnidadDTO(unidad);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UnidadDTO> obtenerListaUnidades() {
        List<Unidad> unidades = unidadRepository.findAll();
        if (unidades.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron unidades registradas");
        }
        return MapperUnidad.convertListUnidadDTO(unidades);
    }

    @Override
    @Transactional
    public UnidadDTO guardarUnidad(UnidadDTO unidadDTO) {
        validarUnidadExistente(unidadDTO.getNombreUnidad());
        Unidad unidad = Unidad.builder()
                .nombreUnidad(unidadDTO.getNombreUnidad())
                .build();
        return MapperUnidad.convertUnidadDTO(unidadRepository.save(unidad));
    }

    @Override
    @Transactional
    public UnidadDTO actualizarUnidad(Long id, UnidadDTO unidadDTO) {
        Unidad unidadActual = unidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UNIDAD_NO_ENCONTRADA + id));

        if (!unidadActual.getNombreUnidad().equalsIgnoreCase(unidadDTO.getNombreUnidad())) {
            validarUnidadExistente(unidadDTO.getNombreUnidad());
        }

        unidadActual.setNombreUnidad(unidadDTO.getNombreUnidad());
        return MapperUnidad.convertUnidadDTO(unidadRepository.save(unidadActual));
    }

    private void validarUnidadExistente(String nombreUnidad) {
        if (unidadRepository.existsByNombreUnidadIgnoreCase(nombreUnidad)) {
            throw new ConflictException(UNIDAD_EXISTENTE);
        }
    }

    @Override
    @Transactional
    public void eliminarUnidad(Long id) {
        Unidad unidad = unidadRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(UNIDAD_NO_ENCONTRADA + id));
        unidadRepository.delete(unidad);
    }
}
