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

import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.ProveedorService;


@RestController
@RequestMapping("/proveedores")
public class ProveedorController {

    @Autowired
    private ProveedorService proveedorService;

    @GetMapping
    public ResponseEntity<?> obtenerTodosLosProveedores() {
        try {
            List<ProveedorDTO> proveedores = proveedorService.obtenerTodosProveedores();
            return ResponseEntity.ok(proveedores);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron proveedores", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarProveedorPorId(@PathVariable("id") Long id) {
        try {
            ProveedorDTO proveedor = proveedorService.buscarProveedorPorId(id);
            return ResponseEntity.ok(proveedor);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Proveedor no encontrado", e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarProveedor(@RequestBody ProveedorDTO proveedor) {
        try {
            ProveedorDTO proveedorGuardado = proveedorService.guardarProveedor(proveedor);
            return ResponseEntity.status(HttpStatus.CREATED).body(proveedorGuardado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Proveedor ya existe", e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarProveedor(@PathVariable("id") Long id, @RequestBody ProveedorDTO proveedor) {
        try {
            ProveedorDTO proveedorActualizado = proveedorService.actualizarProveedor(id, proveedor);
            return ResponseEntity.ok(proveedorActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Proveedor no encontrado", e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Proveedor ya existe con este número de documento", e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarProveedor(@PathVariable("id") Long id) {
        try {
            proveedorService.eliminarProveedor(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Proveedor eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Proveedor no encontrado", e.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}
