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
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Impl.PermisoServiceImpl;

@RestController
@RequestMapping("/permisos")
public class PermisoController {

    @Autowired
    private PermisoServiceImpl permisoService;

    @GetMapping
    public ResponseEntity<?> listarTodosLosPermisos() {
        try {
            List<PermisoDTO> permisos = permisoService.listarTodosLosPermisos();
            return ResponseEntity.ok(permisos);
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Map.of(
                "error", "No se encontraron permisos",
                "detalle", e.getMessage()
            ));
        }
    }
}


