package system.pos.backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Reserva.MesaDTO;
import system.pos.backend.model.Mesa;

public class MapperMesa {

    public static MesaDTO convertMesaDTO(Mesa mesa) {
        if (mesa == null) {
            return null;
        }

        return MesaDTO.builder()
                .idMesa(mesa.getIdMesa())
                .nombreMesa(mesa.getNombreMesa())
                .estado(mesa.getEstado())
                .ultimaActualizacion(mesa.getUltimaActualizacion() != null ? mesa.getUltimaActualizacion().toString() : null)
                .build();
    }

    public static Mesa convertMesa(MesaDTO mesaDTO) {
        if (mesaDTO == null) {
            return null;
        }

        return Mesa.builder()
                .idMesa(mesaDTO.getIdMesa())
                .nombreMesa(mesaDTO.getNombreMesa())
                .estado(mesaDTO.getEstado())
                .build();
    }

    public static List<MesaDTO> convertListMesaDTO(List<Mesa> mesas) {
        if (mesas == null || mesas.isEmpty()) {
            return Collections.emptyList();
        }

        return mesas.stream()
                .map(MapperMesa::convertMesaDTO)
                .collect(Collectors.toList());
    }

    public static List<Mesa> convertListMesa(List<MesaDTO> mesasDTO) {
        if (mesasDTO == null || mesasDTO.isEmpty()) {
            return Collections.emptyList();
        }

        return mesasDTO.stream()
                .map(MapperMesa::convertMesa)
                .collect(Collectors.toList());
    }
}
