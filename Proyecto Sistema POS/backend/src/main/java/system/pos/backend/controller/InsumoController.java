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

import system.pos.backend.dto.Inventario.InsumoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.InsumoService;

@RestController
@RequestMapping("/insumos")
public class InsumoController {

    @Autowired
    private InsumoService insumoService;

    private static final String INSUMO_NO_ENCONTRADO = "Insumo no encontrado";

    @GetMapping
    public ResponseEntity<?> listarInsumos() {
        try {
            List<InsumoDTO> insumos = insumoService.listarInsumos();
            return ResponseEntity.ok(insumos);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron insumos", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarInsumoPorId(@PathVariable("id") Long id) {
        try {
            InsumoDTO insumo = insumoService.buscarInsumoPorId(id);
            return ResponseEntity.ok(insumo);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, INSUMO_NO_ENCONTRADO, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearInsumo(@RequestBody InsumoDTO insumoDTO) {
        try {
            InsumoDTO insumoCreado = insumoService.crearInsumo(insumoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(insumoCreado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al crear el insumo", e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarInsumo(@PathVariable("id") Long id, @RequestBody InsumoDTO insumoDTO) {
        try {
            InsumoDTO insumoActualizado = insumoService.editarInsumo(id, insumoDTO);
            return ResponseEntity.ok(insumoActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, INSUMO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar el insumo", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarInsumo(@PathVariable("id") Long id) {
        try {
            insumoService.eliminarInsumo(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Insumo eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, INSUMO_NO_ENCONTRADO, e.getMessage());
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
