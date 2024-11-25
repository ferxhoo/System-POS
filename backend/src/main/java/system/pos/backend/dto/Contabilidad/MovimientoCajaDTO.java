package system.pos.backend.dto.Contabilidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MovimientoCajaDTO {
    private Long idMovimiento;
    private CajaDTO caja;
    private String fecha;  
    private FormaPagoDTO formaPago;
    private String tipoMovimiento;
    private String monto;  
    private String ultimaActualizacion;
}
