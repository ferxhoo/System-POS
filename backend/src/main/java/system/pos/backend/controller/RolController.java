package system.pos.backend.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;
import system.pos.backend.service.Impl.RolServiceImpl;

@RestController
@RequestMapping("/roles")
public class RolController {

    @Autowired
    private RolServiceImpl rolService;

    @GetMapping
    public ResponseEntity<?> listarTodosLosRols() {
        return ResponseEntity.ok(rolService.listarTodosLosRols());
    }

    @PostMapping
    public ResponseEntity<?> guardarRol(@RequestBody RolGuardarDTO rolGuardar) {
        return ResponseEntity.ok(rolService.guardarRol(rolGuardar));
    }

    @GetMapping("/{idRol}")
    public ResponseEntity<?> obtenerRol(@PathVariable Long idRol) {
        return ResponseEntity.ok(rolService.obtenerRol(idRol));
    }

    @PutMapping("/editar/{idRol}")
    public ResponseEntity<?> actualizarRol(@PathVariable("idRol") Long idRol, @RequestBody RolGuardarDTO rolGuardar) {
        return ResponseEntity.ok(rolService.actualizarRol(idRol, rolGuardar));
    }


    @DeleteMapping("/{idRol}")
    public ResponseEntity<?> eliminarRol(@PathVariable("idRol") Long idRol) {   
        rolService.eliminarRol(idRol);
        return ResponseEntity.ok("Rol eliminado con éxito");
    }

}
