package system.pos.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.UsuarioRolPermiso.PermisoDTO;
import system.pos.backend.service.Interfaces.PermisoService;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public ResponseEntity<?> listarTodosLosPermisos() {
        List<PermisoDTO> permisos = permisoService.listarTodosLosPermisos();
        if (permisos.isEmpty()) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron permisos", "La lista de permisos está vacía");
        }
        return ResponseEntity.ok(permisos);
    }

    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}

