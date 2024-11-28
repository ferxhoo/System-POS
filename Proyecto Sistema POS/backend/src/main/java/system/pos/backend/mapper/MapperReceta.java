package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.RecetaDTO;
import system.pos.backend.model.Receta;

public class MapperReceta {

    // Convertir Receta a RecetaDTO
    public static RecetaDTO convertRecetaDTO(Receta receta) {
        if (receta == null) {
            return null;
        }

        return RecetaDTO.builder()
                .idReceta(receta.getIdReceta())
                .producto(MapperProducto.convertProductoDTO(receta.getProducto()))
                .insumo(MapperInsumo.convertInsumoDTO(receta.getInsumo()))
                .cantidadIngrediente(receta.getCantidadIngrediente() != null ? receta.getCantidadIngrediente().toString() : null)
                .fechaRegistro(receta.getFechaRegistro() != null ? receta.getFechaRegistro().toString() : null)
                .ultimaActualizacion(receta.getUltimaActualizacion() != null ? receta.getUltimaActualizacion().toString() : null)
                .build();
    }

    // Convertir RecetaDTO a Receta
    public static Receta convertReceta(RecetaDTO recetaDTO) {
        if (recetaDTO == null) {
            return null;
        }

        return Receta.builder()
                .idReceta(recetaDTO.getIdReceta())
                .producto(MapperProducto.convertProducto(recetaDTO.getProducto()))
                .insumo(MapperInsumo.convertInsumo(recetaDTO.getInsumo()))
                .cantidadIngrediente(recetaDTO.getCantidadIngrediente() != null ? Double.valueOf(recetaDTO.getCantidadIngrediente()) : null)
                .fechaRegistro(convertStringToDate(recetaDTO.getFechaRegistro()))
                .ultimaActualizacion(convertStringToDate(recetaDTO.getUltimaActualizacion()))
                .build();
    }

    // Convertir lista de Receta a lista de RecetaDTO
    public static List<RecetaDTO> convertListRecetaDTO(List<Receta> recetas) {
        return recetas.stream()
                .map(MapperReceta::convertRecetaDTO)
                .collect(Collectors.toList());
    }

    // Convertir lista de RecetaDTO a lista de Receta
    public static List<Receta> convertListReceta(List<RecetaDTO> recetaDTOs) {
        return recetaDTOs.stream()
                .map(MapperReceta::convertReceta)
                .collect(Collectors.toList());
    }

    // Convertir fecha de String a LocalDateTime (si no es null)
    private static LocalDateTime convertStringToDate(String date) {
        if (date != null && !date.isEmpty()) {
            return LocalDateTime.parse(date);  
        }
        return null;
    }
}

