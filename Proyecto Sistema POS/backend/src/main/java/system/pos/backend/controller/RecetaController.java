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

import system.pos.backend.dto.Inventario.RecetaDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.RecetaService;

@RestController
@RequestMapping("/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    private static final String RECETA_NO_ENCONTRADA = "Receta no encontrada";

    @GetMapping
    public ResponseEntity<?> listarRecetas() {
        try {
            List<RecetaDTO> recetas = recetaService.listarRecetas();
            return ResponseEntity.ok(recetas);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron recetas", e.getMessage());
        }
    }

    @GetMapping("/producto/{idProducto}")
    public ResponseEntity<?> buscarRecetasPorIdProducto(@PathVariable Long idProducto) {
        try {
            List<RecetaDTO> recetas = recetaService.buscarRecetasPorIdProducto(idProducto);
            return ResponseEntity.ok(recetas);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron recetas", e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearReceta(@RequestBody RecetaDTO recetaDTO) {
        try {
            RecetaDTO recetaCreada = recetaService.crearReceta(recetaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(recetaCreada);
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al crear la receta", e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarReceta(@PathVariable Long id, @RequestBody RecetaDTO recetaDTO) {
        try {
            RecetaDTO recetaActualizada = recetaService.editarReceta(id, recetaDTO);
            return ResponseEntity.ok(recetaActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, RECETA_NO_ENCONTRADA, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarReceta(@PathVariable Long id) {
        try {
            recetaService.eliminarReceta(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Receta eliminada con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, RECETA_NO_ENCONTRADA, e.getMessage());
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

