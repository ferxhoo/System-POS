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

import system.pos.backend.dto.Inventario.CategoriaDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.CategoriaService;

@RestController
@RequestMapping("/categorias")
public class CategoriaController {

    @Autowired
    private CategoriaService categoriaService;

    private static final String CATEGORIA_NO_ENCONTRADA = "Categoría no encontrada";

    @GetMapping
    public ResponseEntity<?> obtenerTodasLasCategorias() {
        try {
            List<CategoriaDTO> categorias = categoriaService.obtenerListaCategorias();
            return ResponseEntity.ok(categorias);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron categorías", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCategoriaPorId(@PathVariable("id") Long id) {
        try {
            CategoriaDTO categoria = categoriaService.buscarCategoriaPorId(id);
            return ResponseEntity.ok(categoria);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, CATEGORIA_NO_ENCONTRADA, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarCategoria(@RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoriaGuardada = categoriaService.guardarCategoria(categoriaDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(categoriaGuardada);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al guardar la categoría", e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarCategoria(@PathVariable("id") Long id, @RequestBody CategoriaDTO categoriaDTO) {
        try {
            CategoriaDTO categoriaActualizada = categoriaService.actualizarCategoria(id, categoriaDTO);
            return ResponseEntity.ok(categoriaActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, CATEGORIA_NO_ENCONTRADA, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar la categoría", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCategoria(@PathVariable("id") Long id) {
        try {
            categoriaService.eliminarCategoria(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Categoría eliminada con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, CATEGORIA_NO_ENCONTRADA, e.getMessage());
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
