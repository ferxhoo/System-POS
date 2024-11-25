package system.pos.backend.dto.StakeHolderExternos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorDTO {
    private Long idProveedor;
    private String tipoProveedor;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreProveedor;
    private String direccion;
    private String pais;
    private String departamento;
    private String ciudad;
    private String telefono;
    private String email;
    private String ultimaActualizacion;
}
