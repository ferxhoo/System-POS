package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Reserva.ReservaDTO;
import system.pos.backend.model.Reserva;

public class MapperReserva {

    // Convierte una entidad Reserva en un DTO ReservaDTO
    public static ReservaDTO convertReservaDTO(Reserva reserva) {
        if (reserva == null) {
            return null;
        }
        return ReservaDTO.builder()
                .idReserva(reserva.getIdReserva())
                .mesa(MapperMesa.convertMesaDTO(reserva.getMesa())) // Asumiendo que existe MapperMesa
                .cliente(MapperCliente.convertClienteDTO(reserva.getCliente())) // Asumiendo que existe MapperCliente
                .fechaReserva(reserva.getFechaReserva() != null ? reserva.getFechaReserva().toString() : null)
                .ultimaActualizacion(reserva.getUltimaActualizacion() != null ? reserva.getUltimaActualizacion().toString() : null)
                .estado(reserva.getEstado())
                .observaciones(reserva.getObservaciones())
                .build();
    }

    // Convierte un DTO ReservaDTO en una entidad Reserva
    public static Reserva convertReserva(ReservaDTO reservaDTO) {
        if (reservaDTO == null) {
            return null;
        }
        return Reserva.builder()
                .idReserva(reservaDTO.getIdReserva())
                .mesa(MapperMesa.convertMesa(reservaDTO.getMesa())) // Asumiendo que existe MapperMesa
                .cliente(MapperCliente.convertCliente(reservaDTO.getCliente())) // Asumiendo que existe MapperCliente
                .fechaReserva(reservaDTO.getFechaReserva() != null ? LocalDateTime.parse(reservaDTO.getFechaReserva()) : null)
                .ultimaActualizacion(reservaDTO.getUltimaActualizacion() != null ? LocalDateTime.parse(reservaDTO.getUltimaActualizacion()) : null)
                .estado(reservaDTO.getEstado())
                .observaciones(reservaDTO.getObservaciones())
                .build();
    }

    // Convierte una lista de entidades Reserva en una lista de DTOs ReservaDTO
    public static List<ReservaDTO> convertListReservaDTO(List<Reserva> reservas) {
        if (reservas == null || reservas.isEmpty()) {
            return Collections.emptyList();
        }
        return reservas.stream()
                .map(MapperReserva::convertReservaDTO)
                .collect(Collectors.toList());
    }

    // Convierte una lista de DTOs ReservaDTO en una lista de entidades Reserva
    public static List<Reserva> convertListReserva(List<ReservaDTO> reservaDTOs) {
        if (reservaDTOs == null || reservaDTOs.isEmpty()) {
            return Collections.emptyList();
        }
        return reservaDTOs.stream()
                .map(MapperReserva::convertReserva)
                .collect(Collectors.toList());
    }
}
