package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.UsuarioRolPermiso.PermisoDTO;

public interface PermisoService {
    public void inicializarPermisosDefault();
    public List<PermisoDTO> listarTodosLosPermisos();
}
