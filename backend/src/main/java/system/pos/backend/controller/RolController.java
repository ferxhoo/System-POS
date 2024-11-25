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

import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.RolService;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<?> listarTodosLosRoles() {
        List<RolDTO> roles = rolService.listarTodosLosRols();
        return ResponseEntity.ok(roles);
    }

    @PostMapping
    public ResponseEntity<?> guardarRol(@RequestBody RolGuardarDTO rolGuardar) {
        try {
            RolDTO rolGuardado = rolService.guardarRol(rolGuardar);
            return ResponseEntity.status(HttpStatus.CREATED).body(rolGuardado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Rol duplicado", e.getMessage());
        }
    }

    @GetMapping("/{idRol}")
    public ResponseEntity<?> obtenerRol(@PathVariable("idRol") Long idRol) {
        try {
            RolDTO rol = rolService.obtenerRol(idRol);
            return ResponseEntity.ok(rol);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Rol no encontrado", e.getMessage());
        }
    }

    @PutMapping("/editar/{idRol}")
    public ResponseEntity<?> actualizarRol(@PathVariable("idRol") Long idRol, @RequestBody RolGuardarDTO rolGuardar) {
        try {
            RolDTO rolActualizado = rolService.actualizarRol(idRol, rolGuardar);
            return ResponseEntity.ok(rolActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Rol no encontrado", e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Rol duplicado", e.getMessage());
        }
    }

    @DeleteMapping("/{idRol}")
    public ResponseEntity<?> eliminarRol(@PathVariable("idRol") Long idRol) {
        try {
            rolService.eliminarRol(idRol);
            return ResponseEntity.ok(Collections.singletonMap("message", "Rol eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Rol no encontrado", e.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}
