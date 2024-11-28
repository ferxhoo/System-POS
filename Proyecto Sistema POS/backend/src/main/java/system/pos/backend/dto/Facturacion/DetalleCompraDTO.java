package system.pos.backend.dto.Facturacion;

import lombok.Builder;
import lombok.Data;
import system.pos.backend.dto.Inventario.InsumoDTO;

@Data
@Builder
public class DetalleCompraDTO {
    private Long idDetalle;
    private Long idCompra; // Solo se necesita el ID de la compra, no todo el objeto
    private InsumoDTO insumo; // Referencia al DTO de Insumo
    private Integer cantidadComprada;
    private Double precioUnitario;
    private Double subtotal;
}