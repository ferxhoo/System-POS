package system.pos.backend.mapper;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.CategoriaDTO;
import system.pos.backend.model.Categoria;

public class MapperCategoria {

    public static CategoriaDTO convertCategoriaDTO(Categoria categoria) {
        if (categoria == null) {
            return null;
        }

        return CategoriaDTO.builder()
                .idCategoria(categoria.getIdCategoria())
                .tipoCategoria(categoria.getTipoCategoria())
                .nombreCategoria(categoria.getNombreCategoria())
                .ultimaActualizacion(categoria.getUltimaActualizacion() != null ? categoria.getUltimaActualizacion().toString() : null)
                .build();
    }

    public static Categoria convertCategoria(CategoriaDTO categoriaDTO) {
        if (categoriaDTO == null) {
            return null;
        }

        return Categoria.builder()
                .idCategoria(categoriaDTO.getIdCategoria())
                .tipoCategoria(categoriaDTO.getTipoCategoria())
                .nombreCategoria(categoriaDTO.getNombreCategoria())
                .build();
    }

    public static List<CategoriaDTO> convertListCategoriaDTO(List<Categoria> categorias) {
        if (categorias == null || categorias.isEmpty()) {
            return Collections.emptyList();
        }

        return categorias.stream()
                .map(MapperCategoria::convertCategoriaDTO)
                .collect(Collectors.toList());
    }

    public static List<Categoria> convertListCategoria(List<CategoriaDTO> categoriasDTO) {
        if (categoriasDTO == null || categoriasDTO.isEmpty()) {
            return Collections.emptyList();
        }

        return categoriasDTO.stream()
                .map(MapperCategoria::convertCategoria)
                .collect(Collectors.toList());
    }
}
