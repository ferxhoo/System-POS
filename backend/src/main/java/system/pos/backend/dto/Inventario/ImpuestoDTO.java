package system.pos.backend.dto.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ImpuestoDTO {
    private Long idImpuesto;
    private String nombreImpuesto;
    private String concepto;
    private String tarifa;  
    private String fechaRegistro;
    private String ultimaActualizacion;
}

