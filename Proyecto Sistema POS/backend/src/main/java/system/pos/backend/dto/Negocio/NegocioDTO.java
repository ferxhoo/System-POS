package system.pos.backend.dto.Negocio;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NegocioDTO {
    private String nit;
    private String nombreNegocio;
    private String direccion;
    private String pais;
    private String departamento;
    private String ciudad;
    private String telefono;
    private String email;
    private String representanteLegal;
    private String tipoRegimen;
    private String responsabilidadFiscal;
    private String fechaRegistro;
    private String ultimaActualizacion;
}
