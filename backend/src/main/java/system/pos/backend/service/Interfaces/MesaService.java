package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Reserva.MesaDTO;

import java.util.List;

public interface MesaService {
    MesaDTO buscarMesaPorId(Long id);
    List<MesaDTO> obtenerTodasLasMesas();
    MesaDTO guardarMesa(MesaDTO mesaDTO);
    MesaDTO actualizarMesa(Long id, MesaDTO mesaDTO);
    void eliminarMesa(Long id);
    List<MesaDTO> crearVariasMesas(int cantidad);
}
