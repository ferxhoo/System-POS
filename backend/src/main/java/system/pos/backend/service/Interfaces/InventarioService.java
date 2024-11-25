package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Inventario.InventarioDTO;

public interface InventarioService {
    InventarioDTO sumarCantidad(Long idInsumo, Integer cantidad);
    InventarioDTO restarCantidad(Long idInsumo, Integer cantidad);
    List<InventarioDTO> listarInventario();
}
