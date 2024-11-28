package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Inventario.ProductoDTO;

public interface ProductoService {
    ProductoDTO crearProducto(ProductoDTO productoDTO);
    ProductoDTO buscarProductoPorId(Long id);
    ProductoDTO editarProducto(Long id, ProductoDTO productoDTO);
    void eliminarProducto(Long id);
    List<ProductoDTO> listarProductos();
}
