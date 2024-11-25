package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Inventario.InsumoDTO;

public interface InsumoService {
    InsumoDTO crearInsumo(InsumoDTO insumoDTO);
    InsumoDTO buscarInsumoPorId(Long id);
    InsumoDTO editarInsumo(Long id, InsumoDTO insumoDTO);
    void eliminarInsumo(Long id);
    List<InsumoDTO> listarInsumos();
}
