package system.pos.backend.dto.Contabilidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FormaPagoDTO {
    private Long idFormaPago;
    private String nombreFormaPago;
    private String fechaRegistro;  
    private String ultimaActualizacion;
}