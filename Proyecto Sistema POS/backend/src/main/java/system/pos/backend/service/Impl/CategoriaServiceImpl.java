package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.CategoriaRepository;
import system.pos.backend.dto.Inventario.CategoriaDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperCategoria;
import system.pos.backend.model.Categoria;
import system.pos.backend.service.Interfaces.CategoriaService;

@Service
public class CategoriaServiceImpl implements CategoriaService {

    private static final String CATEGORIA_NO_ENCONTRADA = "Categoría no encontrada con el ID: ";
    private static final String CATEGORIA_EXISTENTE = "Ya existe una categoría con este tipo y nombre: ";

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Override
    @Transactional
    public void inicializarCategoriasDefault() {
        List<Categoria> categoriasPorDefecto = List.of(
            Categoria.builder().tipoCategoria("Insumo").nombreCategoria("Ingredientes").build(),
            Categoria.builder().tipoCategoria("Producto").nombreCategoria("Plato Fuerte").build(),
            Categoria.builder().tipoCategoria("Producto").nombreCategoria("Jugos Naturales").build()
        );

        categoriasPorDefecto.forEach(categoria -> {
            if (!categoriaRepository.existsByTipoCategoriaAndNombreCategoria(categoria.getTipoCategoria(), categoria.getNombreCategoria())) {
                categoriaRepository.save(categoria);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public CategoriaDTO buscarCategoriaPorId(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + id));
        return MapperCategoria.convertCategoriaDTO(categoria);
    }

    @Override
    @Transactional(readOnly = true)
    public List<CategoriaDTO> obtenerListaCategorias() {
        List<Categoria> categorias = categoriaRepository.findAll();
        if (categorias.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron categorías registradas");
        }
        return MapperCategoria.convertListCategoriaDTO(categorias);
    }

    @Override
    @Transactional
    public CategoriaDTO guardarCategoria(CategoriaDTO categoriaDTO) {
        validarCategoriaExistente(categoriaDTO.getTipoCategoria(), categoriaDTO.getNombreCategoria());
        Categoria categoria = MapperCategoria.convertCategoria(categoriaDTO);
        return MapperCategoria.convertCategoriaDTO(categoriaRepository.save(categoria));
    }

    @Override
    @Transactional
    public CategoriaDTO actualizarCategoria(Long id, CategoriaDTO categoriaDTO) {
        Categoria categoriaActual = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + id));

        if (!categoriaActual.getTipoCategoria().equals(categoriaDTO.getTipoCategoria()) 
                || !categoriaActual.getNombreCategoria().equals(categoriaDTO.getNombreCategoria())) {
            validarCategoriaExistente(categoriaDTO.getTipoCategoria(), categoriaDTO.getNombreCategoria());
        }

        categoriaActual = actualizarDatosCategoria(categoriaActual, categoriaDTO);
        return MapperCategoria.convertCategoriaDTO(categoriaRepository.save(categoriaActual));
    }

    @Override
    @Transactional
    public void eliminarCategoria(Long id) {
        Categoria categoria = categoriaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CATEGORIA_NO_ENCONTRADA + id));
        categoriaRepository.delete(categoria);
    }

    // Métodos auxiliares
    private void validarCategoriaExistente(String tipoCategoria, String nombreCategoria) {
        if (categoriaRepository.existsByTipoCategoriaAndNombreCategoria(tipoCategoria, nombreCategoria)) {
            throw new ConflictException(CATEGORIA_EXISTENTE + tipoCategoria + " - " + nombreCategoria);
        }
    }

    private Categoria actualizarDatosCategoria(Categoria categoriaActual, CategoriaDTO categoriaDTO) {
        return categoriaActual.toBuilder()
                .tipoCategoria(categoriaDTO.getTipoCategoria())
                .nombreCategoria(categoriaDTO.getNombreCategoria())
                .build();
    }
}
