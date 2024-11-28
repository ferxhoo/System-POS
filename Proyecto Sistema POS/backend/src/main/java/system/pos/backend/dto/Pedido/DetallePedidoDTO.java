package system.pos.backend.dto.Pedido;

import system.pos.backend.dto.Inventario.ProductoDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DetallePedidoDTO {
    private Long idDetallePedido;
    private ProductoDTO producto;
    private Integer cantidad;
    private String subtotal;
    private String fechaDetallePedido;
    private String ultimaActualizacion;
}
