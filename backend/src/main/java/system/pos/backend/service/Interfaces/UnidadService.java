package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Inventario.UnidadDTO;

import java.util.List;

public interface UnidadService {
    void inicializarUnidadesDefeault();
    UnidadDTO buscarUnidadPorId(Long id);
    List<UnidadDTO> obtenerListaUnidades();
    UnidadDTO guardarUnidad(UnidadDTO unidad);
    UnidadDTO actualizarUnidad(Long id, UnidadDTO unidad);
    void eliminarUnidad(Long id);
}
