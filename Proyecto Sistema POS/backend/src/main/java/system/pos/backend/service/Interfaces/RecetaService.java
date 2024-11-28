package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Inventario.RecetaDTO;

public interface RecetaService {
    RecetaDTO crearReceta(RecetaDTO recetaDTO);
    List<RecetaDTO> listarRecetas();
    List<RecetaDTO> buscarRecetasPorIdProducto(Long idProducto);
    RecetaDTO editarReceta(Long id, RecetaDTO recetaDTO);
    void eliminarReceta(Long id);
}
