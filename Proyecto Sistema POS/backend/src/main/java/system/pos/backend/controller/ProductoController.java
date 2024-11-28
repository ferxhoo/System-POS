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

import system.pos.backend.dto.Inventario.ProductoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.ProductoService;

@RestController
@RequestMapping("/productos")
public class ProductoController {

    @Autowired
    private ProductoService productoService;

    private static final String PRODUCTO_NO_ENCONTRADO = "Producto no encontrado";

    @GetMapping
    public ResponseEntity<?> listarProductos() {
        try {
            List<ProductoDTO> productos = productoService.listarProductos();
            return ResponseEntity.ok(productos);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron productos", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProductoPorId(@PathVariable("id") Long id) {
        try {
            ProductoDTO producto = productoService.buscarProductoPorId(id);
            return ResponseEntity.ok(producto);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PRODUCTO_NO_ENCONTRADO, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> crearProducto(@RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO productoCreado = productoService.crearProducto(productoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(productoCreado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al crear el producto", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado", e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> editarProducto(@PathVariable("id") Long id, @RequestBody ProductoDTO productoDTO) {
        try {
            ProductoDTO productoActualizado = productoService.editarProducto(id, productoDTO);
            return ResponseEntity.ok(productoActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PRODUCTO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar el producto", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProducto(@PathVariable("id") Long id) {
        try {
            productoService.eliminarProducto(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Producto eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PRODUCTO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al eliminar el producto", e.getMessage());
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
