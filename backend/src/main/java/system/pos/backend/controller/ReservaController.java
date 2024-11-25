package system.pos.backend.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.Reserva.ReservaDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.ReservaService;

@RestController
@RequestMapping("/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    @GetMapping
    public ResponseEntity<?> listarTodasLasReservas() {
        List<ReservaDTO> reservas = reservaService.listarTodasLasReservas();
        return ResponseEntity.ok(reservas);
    }

    @PostMapping
    public ResponseEntity<?> guardarReserva(@RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO reservaGuardada = reservaService.guardarReserva(reservaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(reservaGuardada);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al guardar la reserva", e.getMessage());
        }
    }

    @GetMapping("/{idReserva}")
    public ResponseEntity<?> obtenerReserva(@PathVariable("idReserva") Long idReserva) {
        try {
            ReservaDTO reserva = reservaService.obtenerReservaPorId(idReserva);
            return ResponseEntity.ok(reserva);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Reserva no encontrada", e.getMessage());
        }
    }

    @PutMapping("/editar/{idReserva}")
    public ResponseEntity<?> actualizarReserva(@PathVariable("idReserva") Long idReserva, @RequestBody ReservaDTO reservaDTO) {
        try {
            ReservaDTO reservaActualizada = reservaService.actualizarReserva(idReserva, reservaDTO);
            return ResponseEntity.ok(reservaActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Reserva no encontrada", e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar la reserva", e.getMessage());
        }
    }

    @DeleteMapping("/{idReserva}")
    public ResponseEntity<?> eliminarReserva(@PathVariable("idReserva") Long idReserva) {
        try {
            reservaService.eliminarReserva(idReserva);
            return ResponseEntity.ok(Collections.singletonMap("message", "Reserva eliminada con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Reserva no encontrada", e.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}

