package system.pos.backend.dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.Inventario.InsumoDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraDTO {
    private Long idDetalleCompra;
    private CompraDTO compra;
    private InsumoDTO insumo;
    private Integer cantidad;
    private String precioUnitario;
    private String subtotal;  
    private String fechaRegistro;  
    private String ultimaActualizacion;  
}