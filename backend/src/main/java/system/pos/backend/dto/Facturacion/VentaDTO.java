package system.pos.backend.dto.Facturacion; 

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.Reserva.MesaDTO;
import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VentaDTO {
    private Long idVenta;
    private UsuarioDTO usuario;
    private ClienteDTO cliente;
    private MesaDTO mesa;
    private String fechaVenta;  
    private String total;  
}