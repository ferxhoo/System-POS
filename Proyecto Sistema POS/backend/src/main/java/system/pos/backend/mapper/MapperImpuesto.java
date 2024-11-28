package system.pos.backend.mapper;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.ImpuestoDTO;
import system.pos.backend.model.Impuesto;

public class MapperImpuesto {

    public static ImpuestoDTO convertImpuestoDTO(Impuesto impuesto) {
        if (impuesto == null) {
            return null;
        }

        return ImpuestoDTO.builder()
                .idImpuesto(impuesto.getIdImpuesto())
                .nombreImpuesto(impuesto.getNombreImpuesto())
                .concepto(impuesto.getConcepto())
                .tarifa(impuesto.getTarifa() != null ? impuesto.getTarifa().toString() : null)
                .fechaRegistro(impuesto.getFechaRegistro() != null ? impuesto.getFechaRegistro().toString() : null)
                .ultimaActualizacion(impuesto.getUltimaActualizacion() != null ? impuesto.getUltimaActualizacion().toString() : null)
                .build();
    }

    public static Impuesto convertImpuesto(ImpuestoDTO impuestoDTO) {
        if (impuestoDTO == null) {
            return null;
        }

        return Impuesto.builder()
                .idImpuesto(impuestoDTO.getIdImpuesto())
                .nombreImpuesto(impuestoDTO.getNombreImpuesto())
                .concepto(impuestoDTO.getConcepto())
                .tarifa(impuestoDTO.getTarifa() != null ? new BigDecimal(impuestoDTO.getTarifa()) : null)
                .build();
    }

    public static List<ImpuestoDTO> convertListImpuestoDTO(List<Impuesto> impuestos) {
        if (impuestos == null || impuestos.isEmpty()) {
            return Collections.emptyList();
        }

        return impuestos.stream()
                .map(MapperImpuesto::convertImpuestoDTO)
                .collect(Collectors.toList());
    }

    public static List<Impuesto> convertListImpuesto(List<ImpuestoDTO> impuestosDTO) {
        if (impuestosDTO == null || impuestosDTO.isEmpty()) {
            return Collections.emptyList();
        }

        return impuestosDTO.stream()
                .map(MapperImpuesto::convertImpuesto)
                .collect(Collectors.toList());
    }
}

