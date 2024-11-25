package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Reserva.ReservaDTO;

public interface ReservaService {
    List<ReservaDTO> listarTodasLasReservas();
    ReservaDTO guardarReserva(ReservaDTO reservaDTO);
    ReservaDTO obtenerReservaPorId(Long idReserva);
    ReservaDTO actualizarReserva(Long idReserva, ReservaDTO reservaDTO);
    void eliminarReserva(Long idReserva);
}
