package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;

public interface RolService {
    void inicializarRolsDefault();
    RolDTO guardarRol(RolGuardarDTO rolGuardar);
    List<RolDTO> listarTodosLosRols();
    RolDTO obtenerRol(Long idRol);
    RolDTO actualizarRol(Long idRol, RolGuardarDTO rolDTO);
    void eliminarRol(Long idRol);
}
