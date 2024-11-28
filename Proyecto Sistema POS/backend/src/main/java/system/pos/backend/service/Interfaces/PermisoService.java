package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.UsuarioRolPermiso.PermisoDTO;

public interface PermisoService {
    void inicializarPermisosDefault();
    List<PermisoDTO> listarTodosLosPermisos();
}
