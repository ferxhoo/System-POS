package system.pos.backend.dto.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InsumoDTO {
    private Long idInsumo;
    private CategoriaDTO categoria;
    private String nombreInsumo;
    private UnidadDTO unidad;
    private String ultimaActualizacion;
}
