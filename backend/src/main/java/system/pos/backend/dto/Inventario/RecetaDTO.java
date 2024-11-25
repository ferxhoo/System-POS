package system.pos.backend.dto.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RecetaDTO {
    private Long idReceta;
    private ProductoDTO producto;
    private InsumoDTO insumo;
    private String cantidadIngrediente;  
    private String fechaRegistro;
    private String ultimaActualizacion;
}

