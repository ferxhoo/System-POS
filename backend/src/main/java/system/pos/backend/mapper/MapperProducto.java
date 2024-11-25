package system.pos.backend.mapper;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Inventario.ProductoDTO;
import system.pos.backend.model.Producto;

public class MapperProducto {

    // Convertir Producto a ProductoDTO
    public static ProductoDTO convertProductoDTO(Producto producto) {
        if (producto == null) {
            return null;
        }
    
        return ProductoDTO.builder()
                .idProducto(producto.getIdProducto())
                .categoria(MapperCategoria.convertCategoriaDTO(producto.getCategoria()))
                .nombreProducto(producto.getNombreProducto())
                .inventariable(producto.isInventariable())
                .precioUnitario(producto.getPrecioUnitario() != null ? producto.getPrecioUnitario().toString() : null)  
                .impuesto(MapperImpuesto.convertImpuestoDTO(producto.getImpuesto()))
                .fechaRegistro(producto.getFechaRegistro() != null ? producto.getFechaRegistro().toString() : null)  
                .ultimaActualizacion(producto.getUltimaActualizacion() != null ? producto.getUltimaActualizacion().toString() : null)  
                .build();
    }

    // Convertir ProductoDTO a Producto
    public static Producto convertProducto(ProductoDTO productoDTO) {
        if (productoDTO == null) {
            return null;
        }
    
        return Producto.builder()
                .idProducto(productoDTO.getIdProducto())
                .categoria(MapperCategoria.convertCategoria(productoDTO.getCategoria()))
                .nombreProducto(productoDTO.getNombreProducto())
                .inventariable(productoDTO.isInventariable())
                .precioUnitario(productoDTO.getPrecioUnitario() != null ? new BigDecimal(productoDTO.getPrecioUnitario()) : null)  
                .impuesto(MapperImpuesto.convertImpuesto(productoDTO.getImpuesto()))
                .fechaRegistro(convertStringToDate(productoDTO.getFechaRegistro()))  
                .ultimaActualizacion(convertStringToDate(productoDTO.getUltimaActualizacion()))  
                .build();
    }

    // Convertir fecha de String a LocalDateTime (si no es null)
    private static LocalDateTime convertStringToDate(String date) {
        if (date != null && !date.isEmpty()) {
            return LocalDateTime.parse(date);  // Asume el formato ISO_DATE_TIME
        }
        return null;
    }

    // Convertir lista de Producto a lista de ProductoDTO
    public static List<ProductoDTO> convertListProductoDTO(List<Producto> productos) {
        return productos.stream()
                .map(MapperProducto::convertProductoDTO)
                .collect(Collectors.toList());
    }

    // Convertir lista de ProductoDTO a lista de Producto
    public static List<Producto> convertListProducto(List<ProductoDTO> productoDTOs) {
        return productoDTOs.stream()
                .map(MapperProducto::convertProducto)
                .collect(Collectors.toList());
    }
}
