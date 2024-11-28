package system.pos.backend.dto.Inventario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UnidadDTO {
    private Long idUnidad;
    private String nombreUnidad;
    private String ultimaActualizacion;
}

