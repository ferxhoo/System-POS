package system.pos.backend.dto.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductoDTO {
    private Long idProducto;
    private CategoriaDTO categoria;
    private String nombreProducto;
    private boolean inventariable;
    private String precioUnitario; // Guardado como String para evitar problemas de precisión
    private ImpuestoDTO impuesto;
    private String fechaRegistro;
    private String ultimaActualizacion;
}
