package system.pos.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.Inventario.InventarioDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.InventarioService;

@RestController
@RequestMapping("/inventario")
public class InventarioController {

    @Autowired
    private InventarioService inventarioService;

    private static final String INVENTARIO_NO_ENCONTRADO = "Inventario no encontrado";

    @GetMapping
    public ResponseEntity<?> listarInventario() {
        try {
            List<InventarioDTO> inventario = inventarioService.listarInventario();
            return ResponseEntity.ok(inventario);
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar el inventario", e.getMessage());
        }
    }

    @PostMapping("/sumar/{idInsumo}")
    public ResponseEntity<?> sumarCantidad(@PathVariable Long idInsumo, @RequestParam Integer cantidad) {
        try {
            InventarioDTO inventarioActualizado = inventarioService.sumarCantidad(idInsumo, cantidad);
            return ResponseEntity.ok(inventarioActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, INVENTARIO_NO_ENCONTRADO, e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al sumar al inventario", e.getMessage());
        }
    }

    @PostMapping("/restar/{idInsumo}")
    public ResponseEntity<?> restarCantidad(@PathVariable Long idInsumo, @RequestParam Integer cantidad) {
        try {
            InventarioDTO inventarioActualizado = inventarioService.restarCantidad(idInsumo, cantidad);
            return ResponseEntity.ok(inventarioActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, INVENTARIO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al restar del inventario", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al restar del inventario", e.getMessage());
        }
    }

    // Método genérico para generar respuestas de error
    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}
