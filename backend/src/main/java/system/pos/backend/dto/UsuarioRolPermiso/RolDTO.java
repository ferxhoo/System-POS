package system.pos.backend.dto.UsuarioRolPermiso;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RolDTO {
    private Long idRol;
    private String nombreRol;
    private List<PermisoDTO> permisos;
    private String ultimaActualizacion;
}