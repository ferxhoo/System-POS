package system.pos.backend.dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.Contabilidad.FormaPagoDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePagoDTO {
    private Long idDetallePago;
    private VentaDTO venta;
    private FormaPagoDTO formaPago;
    private String monto;  
    private String fechaDetallePago;  
    private String ultimaActualizacion;  
}
