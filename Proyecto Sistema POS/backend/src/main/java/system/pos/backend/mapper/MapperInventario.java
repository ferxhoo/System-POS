package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.InventarioDTO;
import system.pos.backend.model.Inventario;

public class MapperInventario {

    // Convertir Inventario a InventarioDTO
    public static InventarioDTO convertInventarioDTO(Inventario inventario) {
        if (inventario == null) {
            return null;
        }

        return InventarioDTO.builder()
                .id(inventario.getId())
                .insumo(MapperInsumo.convertInsumoDTO(inventario.getInsumo()))
                .cantidadDisponible(inventario.getCantidadDisponible())
                .ultimaActualizacion(inventario.getUltimaActualizacion() != null ? inventario.getUltimaActualizacion().toString() : null)
                .build();
    }

    // Convertir InventarioDTO a Inventario
    public static Inventario convertInventario(InventarioDTO inventarioDTO) {
        if (inventarioDTO == null) {
            return null;
        }

        return Inventario.builder()
                .id(inventarioDTO.getId())
                .insumo(MapperInsumo.convertInsumo(inventarioDTO.getInsumo()))
                .cantidadDisponible(inventarioDTO.getCantidadDisponible())
                .ultimaActualizacion(convertStringToDate(inventarioDTO.getUltimaActualizacion()))
                .build();
    }

    // Convertir lista de Inventario a lista de InventarioDTO
    public static List<InventarioDTO> convertListInventarioDTO(List<Inventario> inventarios) {
        return inventarios.stream()
                .map(MapperInventario::convertInventarioDTO)
                .collect(Collectors.toList());
    }

    // Convertir lista de InventarioDTO a lista de Inventario
    public static List<Inventario> convertListInventario(List<InventarioDTO> inventarioDTOs) {
        return inventarioDTOs.stream()
                .map(MapperInventario::convertInventario)
                .collect(Collectors.toList());
    }

    // Convertir fecha de String a LocalDateTime (si no es null)
    private static LocalDateTime convertStringToDate(String date) {
        if (date != null && !date.isEmpty()) {
            return LocalDateTime.parse(date); // Asume formato ISO_DATE_TIME
        }
        return null;
    }
}

