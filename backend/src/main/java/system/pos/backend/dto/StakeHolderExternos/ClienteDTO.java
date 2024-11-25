package system.pos.backend.dto.StakeHolderExternos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ClienteDTO {
    private Long idCliente;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombreCompleto;
    private String primerApellido;
    private String segundoApellido;
    private String direccion;
    private String pais;
    private String departamento;
    private String ciudad;
    private String telefono;
    private String email;
    private String ultimaActualizacion;
}

