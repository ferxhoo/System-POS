package system.pos.backend.dto.Reserva;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MesaDTO {
    private Long idMesa;
    private String nombreMesa;
    private String estado;
    private String ultimaActualizacion;
}
