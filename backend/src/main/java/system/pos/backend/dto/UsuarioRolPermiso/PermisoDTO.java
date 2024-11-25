package system.pos.backend.dto.UsuarioRolPermiso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PermisoDTO {
    private Long idPermiso;
    private String modulo;
    private String nombrePermiso;
    private String ruta;
    private String fechaRegistro;
}