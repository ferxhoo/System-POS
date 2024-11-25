package system.pos.backend.dto.Inventario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InventarioDTO {
    private Long id;
    private InsumoDTO insumo;
    private Integer cantidadDisponible;
    private String ultimaActualizacion;
}

