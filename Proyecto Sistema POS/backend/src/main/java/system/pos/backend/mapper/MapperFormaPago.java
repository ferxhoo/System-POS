package system.pos.backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Contabilidad.FormaPagoDTO;
import system.pos.backend.model.FormaPago;

public class MapperFormaPago {

    public static FormaPagoDTO convertFormaPagoDTO(FormaPago formaPago) {
        if (formaPago == null) {
            return null;
        }

        return FormaPagoDTO.builder()
                .idFormaPago(formaPago.getIdFormaPago())
                .nombreFormaPago(formaPago.getNombreFormaPago())
                .fechaRegistro(formaPago.getFechaRegistro() != null ? formaPago.getFechaRegistro().toString() : null)
                .ultimaActualizacion(formaPago.getUltimaActualizacion() != null ? formaPago.getUltimaActualizacion().toString() : null)
                .build();
    }

    public static FormaPago convertFormaPago(FormaPagoDTO formaPagoDTO) {
        if (formaPagoDTO == null) {
            return null;
        }

        return FormaPago.builder()
                .idFormaPago(formaPagoDTO.getIdFormaPago())
                .nombreFormaPago(formaPagoDTO.getNombreFormaPago())
                .build();
    }

    public static List<FormaPagoDTO> convertListFormaPagoDTO(List<FormaPago> formasPago) {
        if (formasPago == null || formasPago.isEmpty()) {
            return Collections.emptyList();
        }

        return formasPago.stream()
                .map(MapperFormaPago::convertFormaPagoDTO)
                .collect(Collectors.toList());
    }

    public static List<FormaPago> convertListFormaPago(List<FormaPagoDTO> formasPagoDTO) {
        if (formasPagoDTO == null || formasPagoDTO.isEmpty()) {
            return Collections.emptyList();
        }

        return formasPagoDTO.stream()
                .map(MapperFormaPago::convertFormaPago)
                .collect(Collectors.toList());
    }
}

