package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.pos.backend.Repository.InsumoRepository;
import system.pos.backend.Repository.ProductoRepository;
import system.pos.backend.Repository.RecetaRepository;
import system.pos.backend.dto.Inventario.RecetaDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperReceta;
import system.pos.backend.model.Insumo;
import system.pos.backend.model.Producto;
import system.pos.backend.model.Receta;
import system.pos.backend.service.Interfaces.RecetaService;

@Service
public class RecetaServiceImpl implements RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private InsumoRepository insumoRepository;

    // Crear receta
    @Override
    public RecetaDTO crearReceta(RecetaDTO recetaDTO) {
        Producto producto = productoRepository.findById(recetaDTO.getProducto().getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + recetaDTO.getProducto().getIdProducto()));

        Insumo insumo = insumoRepository.findById(recetaDTO.getInsumo().getIdInsumo())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + recetaDTO.getInsumo().getIdInsumo()));

        Receta receta = MapperReceta.convertReceta(recetaDTO);
        receta.setProducto(producto);
        receta.setInsumo(insumo);

        Receta recetaGuardada = recetaRepository.save(receta);
        return MapperReceta.convertRecetaDTO(recetaGuardada);
    }

    // Listar todas las recetas
    @Override
    public List<RecetaDTO> listarRecetas() {
        List<Receta> recetas = recetaRepository.findAll();
        return MapperReceta.convertListRecetaDTO(recetas);
    }

    // Buscar recetas por ID de producto
    @Override
    public List<RecetaDTO> buscarRecetasPorIdProducto(Long idProducto) {
        List<Receta> recetas = recetaRepository.findByProducto_IdProducto(idProducto);
        if (recetas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron recetas para el producto con ID: " + idProducto);
        }
        return MapperReceta.convertListRecetaDTO(recetas);
    }

    // Editar receta
    @Override
    public RecetaDTO editarReceta(Long id, RecetaDTO recetaDTO) {
        Receta recetaExistente = recetaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Receta no encontrada con ID: " + id));

        Producto producto = productoRepository.findById(recetaDTO.getProducto().getIdProducto())
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + recetaDTO.getProducto().getIdProducto()));

        Insumo insumo = insumoRepository.findById(recetaDTO.getInsumo().getIdInsumo())
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + recetaDTO.getInsumo().getIdInsumo()));

        recetaExistente.setProducto(producto);
        recetaExistente.setInsumo(insumo);
        recetaExistente.setCantidadIngrediente(Double.valueOf(recetaDTO.getCantidadIngrediente()));

        Receta recetaActualizada = recetaRepository.save(recetaExistente);
        return MapperReceta.convertRecetaDTO(recetaActualizada);
    }

    // Eliminar receta
    @Override
    public void eliminarReceta(Long id) {
        if (!recetaRepository.existsById(id)) {
            throw new ResourceNotFoundException("Receta no encontrada con ID: " + id);
        }
        recetaRepository.deleteById(id);
    }
}

