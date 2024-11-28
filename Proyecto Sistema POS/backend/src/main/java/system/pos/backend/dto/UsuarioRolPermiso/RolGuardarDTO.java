package system.pos.backend.dto.UsuarioRolPermiso;

import java.util.List;

import lombok.Data;

@Data
public class RolGuardarDTO {
    private String nombreRol;
    private List<PermisoGuardarRolDTO> permisos;
}
