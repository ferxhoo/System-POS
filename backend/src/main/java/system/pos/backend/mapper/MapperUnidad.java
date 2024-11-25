package system.pos.backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.UnidadDTO;
import system.pos.backend.model.Unidad;

public class MapperUnidad {

    public static UnidadDTO convertUnidadDTO(Unidad unidad) {
        if (unidad == null) {
            return null;
        }

        return UnidadDTO.builder()
                .idUnidad(unidad.getIdUnidad())
                .nombreUnidad(unidad.getNombreUnidad())
                .ultimaActualizacion(unidad.getUltimaActualizacion() != null ? unidad.getUltimaActualizacion().toString() : null)
                .build();
    }

    public static Unidad convertUnidad(UnidadDTO unidadDTO) {
        if (unidadDTO == null) {
            return null;
        }

        return Unidad.builder()
                .idUnidad(unidadDTO.getIdUnidad())
                .nombreUnidad(unidadDTO.getNombreUnidad())
                .build();
    }

    public static List<UnidadDTO> convertListUnidadDTO(List<Unidad> unidades) {
        if (unidades == null || unidades.isEmpty()) {
            return Collections.emptyList();
        }

        return unidades.stream()
                .map(MapperUnidad::convertUnidadDTO)
                .collect(Collectors.toList());
    }

    public static List<Unidad> convertListUnidad(List<UnidadDTO> unidadesDTO) {
        if (unidadesDTO == null || unidadesDTO.isEmpty()) {
            return Collections.emptyList();
        }

        return unidadesDTO.stream()
                .map(MapperUnidad::convertUnidad)
                .collect(Collectors.toList());
    }
}

