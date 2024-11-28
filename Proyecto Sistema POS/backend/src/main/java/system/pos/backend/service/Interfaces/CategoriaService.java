package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Inventario.CategoriaDTO;

import java.util.List;

public interface CategoriaService {
    void inicializarCategoriasDefault();
    CategoriaDTO buscarCategoriaPorId(Long id);
    List<CategoriaDTO> obtenerListaCategorias();
    CategoriaDTO guardarCategoria(CategoriaDTO categoriaDTO);
    CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO);
    void eliminarCategoria(Long id);
}
