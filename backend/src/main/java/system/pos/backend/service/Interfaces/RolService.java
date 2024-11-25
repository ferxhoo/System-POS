package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;

public interface RolService {
    public void inicializarRolsDefault();
    public RolDTO guardarRol(RolGuardarDTO rolGuardar);
    public List<RolDTO> listarTodosLosRols();
    public RolDTO obtenerRol(Long idRol);
    public RolDTO actualizarRol(Long idRol, RolGuardarDTO rolDTO);
    public void eliminarRol(Long idRol);
}
