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

import system.pos.backend.dto.Inventario.UnidadDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.UnidadService;

@RestController
@RequestMapping("/unidades")
public class UnidadController {

    @Autowired
    private UnidadService unidadService;

    private static final String UNIDAD_NO_ENCONTRADA = "Unidad no encontrada";
    private static final String UNIDAD_YA_EXISTE = "Unidad ya existe";

    @GetMapping
    public ResponseEntity<?> obtenerTodasUnidades() {
        try {
            List<UnidadDTO> unidades = unidadService.obtenerListaUnidades();
            return ResponseEntity.ok(unidades);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron unidades", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarUnidadPorId(@PathVariable("id") Long id) {
        try {
            UnidadDTO unidad = unidadService.buscarUnidadPorId(id);
            return ResponseEntity.ok(unidad);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, UNIDAD_NO_ENCONTRADA, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarUnidad(@RequestBody UnidadDTO unidad) {
        try {
            UnidadDTO unidadGuardada = unidadService.guardarUnidad(unidad);
            return ResponseEntity.status(HttpStatus.CREATED).body(unidadGuardada);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, UNIDAD_YA_EXISTE, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarUnidad(@PathVariable("id") Long id, @RequestBody UnidadDTO unidad) {
        try {
            UnidadDTO unidadActualizada = unidadService.actualizarUnidad(id, unidad);
            return ResponseEntity.ok(unidadActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, UNIDAD_NO_ENCONTRADA, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Unidad ya existe con este nombre", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarUnidad(@PathVariable("id") Long id) {
        try {
            unidadService.eliminarUnidad(id);
            return ResponseEntity.ok(Collections.singletonMap("deleted", true));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, UNIDAD_NO_ENCONTRADA, e.getMessage());
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
