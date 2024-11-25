package system.pos.backend.dto.Reserva;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ReservaDTO {
    private Long idReserva;
    private MesaDTO mesa;
    private ClienteDTO cliente;
    private String fechaReserva;
    private String ultimaActualizacion;
    private String estado;
    private String observaciones;
}

