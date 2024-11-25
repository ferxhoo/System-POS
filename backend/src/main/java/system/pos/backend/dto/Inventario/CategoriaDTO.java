package system.pos.backend.dto.Inventario;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CategoriaDTO {
    private Long idCategoria;
    private String tipoCategoria;
    private String nombreCategoria;
    private String ultimaActualizacion;
}
