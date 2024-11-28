package system.pos.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.InsumoDTO;
import system.pos.backend.model.Insumo;

public class MapperInsumo {

    public static InsumoDTO convertInsumoDTO(Insumo insumo) {
        if (insumo == null) {
            return null;
        }
        return InsumoDTO.builder()
                .idInsumo(insumo.getId())
                .categoria(MapperCategoria.convertCategoriaDTO(insumo.getCategoria()))
                .nombreInsumo(insumo.getNombreInsumo())
                .unidad(MapperUnidad.convertUnidadDTO(insumo.getUnidad()))
                .ultimaActualizacion(insumo.getUltimaActualizacion() != null 
                                     ? insumo.getUltimaActualizacion().toString() 
                                     : null)
                .build();
    }

    public static Insumo convertInsumo(InsumoDTO insumoDTO) {
        if (insumoDTO == null) {
            return null;
        }
        return Insumo.builder()
                .id(insumoDTO.getIdInsumo())
                .categoria(MapperCategoria.convertCategoria(insumoDTO.getCategoria()))
                .nombreInsumo(insumoDTO.getNombreInsumo())
                .unidad(MapperUnidad.convertUnidad(insumoDTO.getUnidad()))
                .build();
    }

    public static List<InsumoDTO> convertListInsumoDTO(List<Insumo> insumos) {
        if (insumos == null || insumos.isEmpty()) {
            return List.of();
        }
        return insumos.stream()
                .map(MapperInsumo::convertInsumoDTO)
                .collect(Collectors.toList());
    }

    public static List<Insumo> convertListInsumo(List<InsumoDTO> insumoDTOs) {
        if (insumoDTOs == null || insumoDTOs.isEmpty()) {
            return List.of();
        }
        return insumoDTOs.stream()
                .map(MapperInsumo::convertInsumo)
                .collect(Collectors.toList());
    }
}

