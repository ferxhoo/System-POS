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

import system.pos.backend.dto.Reserva.MesaDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.MesaService;

@RestController
@RequestMapping("/mesas")
public class MesaController {

    @Autowired
    private MesaService mesaService;

    private static final String MESA_NO_ENCONTRADA = "Mesa no encontrada";
    private static final String ERROR_AL_GUARDAR_MESA = "Error al guardar la mesa";
    private static final String CANTIDAD_INVALIDA = "Cantidad inválida";

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasMesas() {
        try {
            List<MesaDTO> mesas = mesaService.obtenerTodasLasMesas();
            return ResponseEntity.ok(mesas);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron mesas", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarMesaPorId(@PathVariable("id") Long id) {
        try {
            MesaDTO mesa = mesaService.buscarMesaPorId(id);
            return ResponseEntity.ok(mesa);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, MESA_NO_ENCONTRADA, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarMesa(@RequestBody MesaDTO mesa) {
        try {
            MesaDTO mesaGuardada = mesaService.guardarMesa(mesa);
            return ResponseEntity.status(HttpStatus.CREATED).body(mesaGuardada);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, ERROR_AL_GUARDAR_MESA, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarMesa(@PathVariable("id") Long id, @RequestBody MesaDTO mesa) {
        try {
            MesaDTO mesaActualizada = mesaService.actualizarMesa(id, mesa);
            return ResponseEntity.ok(mesaActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, MESA_NO_ENCONTRADA, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarMesa(@PathVariable("id") Long id) {
        try {
            mesaService.eliminarMesa(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Mesa eliminada con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, MESA_NO_ENCONTRADA, e.getMessage());
        }
    }

    @PostMapping("/crear-varias/{cantidad}")
    public ResponseEntity<?> crearVariasMesas(@PathVariable("cantidad") int cantidad) {
        try {
            List<MesaDTO> mesasCreadas = mesaService.crearVariasMesas(cantidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(mesasCreadas);
        } catch (IllegalArgumentException e) {
            return generarRespuestaError(HttpStatus.BAD_REQUEST, CANTIDAD_INVALIDA, e.getMessage());
        }
    }

    // Método para generar respuestas de error de manera centralizada
    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}
