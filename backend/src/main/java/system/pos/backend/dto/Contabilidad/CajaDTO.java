package system.pos.backend.dto.Contabilidad;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CajaDTO {
    private Long idCaja;
    private String fechaApertura;  
    private String montoInicial;  
    private String fechaCierre;  
    private String montoFinal;  
    private String estado;
}