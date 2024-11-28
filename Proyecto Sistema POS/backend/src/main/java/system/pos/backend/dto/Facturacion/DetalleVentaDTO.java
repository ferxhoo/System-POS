package system.pos.backend.dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.Inventario.ProductoDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleVentaDTO {
    private Long idDetalleVenta;
    private VentaDTO venta;
    private ProductoDTO producto;
    private Integer cantidad;
    private String precioUnitario;  
    private String subtotal;  
    private String fechaDetalleVenta; 
    private String ultimaActualizacion;  
}