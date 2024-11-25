package system.pos.backend.dto.UsuarioRolPermiso;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioDTO {
    private Long idUsuario;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreCompleto;
    private String primerApellido;
    private String segundoApellido;
    private String username;
    private String email;
    private String telefono;
    private RolDTO rol;
    private String fechaCreacion;
    private String ultimaActualizacion;
}
