package system.pos.backend.service.Impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.CategoriaRepository;
import system.pos.backend.Repository.ImpuestoRepository;
import system.pos.backend.Repository.InsumoRepository;
import system.pos.backend.Repository.InventarioRepository;
import system.pos.backend.Repository.ProductoRepository;
import system.pos.backend.dto.Inventario.ProductoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperProducto;
import system.pos.backend.model.Categoria;
import system.pos.backend.model.Impuesto;
import system.pos.backend.model.Insumo;
import system.pos.backend.model.Inventario;
import system.pos.backend.model.Producto;
import system.pos.backend.service.Interfaces.ProductoService;

@Service
public class ProductoServiceImpl implements ProductoService {

    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private InsumoRepository insumoRepository;
    @Autowired
    private ImpuestoRepository impuestoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;

    // Crear un producto
    @Override
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + productoDTO.getCategoria().getIdCategoria()));

        Impuesto impuesto = impuestoRepository.findById(productoDTO.getImpuesto().getIdImpuesto())
                .orElseThrow(() -> new ResourceNotFoundException("Impuesto no encontrado con ID: " + productoDTO.getImpuesto().getIdImpuesto()));

        Producto producto = MapperProducto.convertProducto(productoDTO);
        producto.setCategoria(categoria);
        producto.setImpuesto(impuesto);

        // Verificar si el producto es inventariable
        if (productoDTO.isInventariable()) {
            Insumo insumo = new Insumo();
            insumo.setNombreInsumo(productoDTO.getNombreProducto());
            insumo.setCategoria(categoria);

            Insumo insumoGuardado = insumoRepository.save(insumo);

            // Inicializar inventario en 0
            Inventario inventario = new Inventario();
            inventario.setInsumo(insumoGuardado);
            inventario.setCantidadDisponible(0);
            inventarioRepository.save(inventario);

            // Asociar el insumo al producto
            producto.setInventariable(true);
        } else {
            producto.setInventariable(false);
        }

        Producto productoGuardado = productoRepository.save(producto);
        return MapperProducto.convertProductoDTO(productoGuardado);
    }

    // Buscar un producto por ID
    @Override
    public ProductoDTO buscarProductoPorId(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));
        return MapperProducto.convertProductoDTO(producto);
    }

    // Editar un producto
    @Override
    public ProductoDTO editarProducto(Long id, ProductoDTO productoDTO) {
        Producto productoExistente = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        Categoria categoria = categoriaRepository.findById(productoDTO.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoría no encontrada con ID: " + productoDTO.getCategoria().getIdCategoria()));

        Impuesto impuesto = impuestoRepository.findById(productoDTO.getImpuesto().getIdImpuesto())
                .orElseThrow(() -> new ResourceNotFoundException("Impuesto no encontrado con ID: " + productoDTO.getImpuesto().getIdImpuesto()));

        productoExistente.setNombreProducto(productoDTO.getNombreProducto());
        productoExistente.setCategoria(categoria);
        productoExistente.setPrecioUnitario(new BigDecimal(productoDTO.getPrecioUnitario()));
        productoExistente.setImpuesto(impuesto);

        Producto productoActualizado = productoRepository.save(productoExistente);
        return MapperProducto.convertProductoDTO(productoActualizado);
    }

    // Eliminar un producto
    @Override
    @Transactional
    public void eliminarProducto(Long id) {
        Producto producto = productoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + id));

        // Si es inventariable, verificar que no tenga inventarios pendientes
        if (producto.isInventariable()) {
            Insumo insumo = insumoRepository.findByNombreInsumo(producto.getNombreProducto());
            if (insumo != null) {
                Inventario inventario = inventarioRepository.findByInsumo_Id(insumo.getId());
                if (inventario != null && inventario.getCantidadDisponible() > 0) {
                    throw new ConflictException("No se puede eliminar el producto porque tiene inventarios asignados");
                }
                inventarioRepository.deleteByInsumo_Id(insumo.getId());
                insumoRepository.delete(insumo);
            }
        }

        productoRepository.deleteById(id);
    }

    // Listar todos los productos
    @Override
    public List<ProductoDTO> listarProductos() {
        List<Producto> productos = productoRepository.findAll();
        return MapperProducto.convertListProductoDTO(productos);
    }
}
