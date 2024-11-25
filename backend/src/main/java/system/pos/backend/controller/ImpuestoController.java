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

import system.pos.backend.dto.Inventario.ImpuestoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.ImpuestoService;
@RestController
@RequestMapping("/impuestos")
public class ImpuestoController {

    @Autowired
    private ImpuestoService impuestoService;

    private static final String IMPUESTO_NO_ENCONTRADO = "Impuesto no encontrado";
    private static final String IMPUESTO_EXISTENTE = "Impuesto ya existe";
    private static final String IMPUESTO_ERROR_ACTUALIZACION = "Error al actualizar el impuesto";

    @GetMapping
    public ResponseEntity<?> obtenerTodosImpuestos() {
        try {
            List<ImpuestoDTO> impuestos = impuestoService.obtenerListaImpuestos();
            return ResponseEntity.ok(impuestos);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron impuestos", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarImpuestoPorId(@PathVariable("id") Long id) {
        try {
            ImpuestoDTO impuesto = impuestoService.buscarImpuestoPorId(id);
            return ResponseEntity.ok(impuesto);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, IMPUESTO_NO_ENCONTRADO, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarImpuesto(@RequestBody ImpuestoDTO impuesto) {
        try {
            ImpuestoDTO impuestoGuardado = impuestoService.guardarImpuesto(impuesto);
            return ResponseEntity.status(HttpStatus.CREATED).body(impuestoGuardado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, IMPUESTO_EXISTENTE, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarImpuesto(@PathVariable("id") Long id, @RequestBody ImpuestoDTO impuesto) {
        try {
            ImpuestoDTO impuestoActualizado = impuestoService.actualizarImpuesto(id, impuesto);
            return ResponseEntity.ok(impuestoActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, IMPUESTO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, IMPUESTO_ERROR_ACTUALIZACION, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarImpuesto(@PathVariable("id") Long id) {
        try {
            impuestoService.eliminarImpuesto(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Impuesto eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, IMPUESTO_NO_ENCONTRADO, e.getMessage());
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
