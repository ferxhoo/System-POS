package system.pos.backend.dto.Pedido;

import java.util.List;

import system.pos.backend.dto.Reserva.MesaDTO;
import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PedidoDTO {
    private Long idPedido;
    private MesaDTO mesa;
    private UsuarioDTO usuario;
    private ClienteDTO cliente;
    private String fechaPedido;
    private String ultimaActualizacion;
    private String estado;
    private List<DetallePedidoDTO> detalles;
}
