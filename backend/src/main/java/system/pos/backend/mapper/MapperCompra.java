package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Facturacion.CompraDTO;
import system.pos.backend.dto.Facturacion.DetalleCompraDTO;
import system.pos.backend.model.Compra;
import system.pos.backend.model.DetalleCompra;

public class MapperCompra {

    // Convertir DetalleCompra a DetalleCompraDTO
    public static DetalleCompraDTO convertDetalleCompra(DetalleCompra detalle) {
        return DetalleCompraDTO.builder()
                .idDetalle(detalle.getIdDetalle())
                .idCompra(detalle.getCompra().getIdCompra())
                .insumo(MapperInsumo.convertInsumoDTO(detalle.getInsumo()))
                .cantidadComprada(detalle.getCantidadComprada())
                .precioUnitario(detalle.getPrecioUnitario())
                .subtotal(detalle.getSubtotal())
                .build();
    }

    // Convertir DetalleCompraDTO a DetalleCompra
    public static DetalleCompra convertDetalleCompraDTO(DetalleCompraDTO detalleDTO) {
        return DetalleCompra.builder()
                .idDetalle(detalleDTO.getIdDetalle())
                .compra(Compra.builder().idCompra(detalleDTO.getIdCompra()).build()) // Solo se usa el ID para evitar cargar todo el objeto
                .insumo(MapperInsumo.convertInsumo(detalleDTO.getInsumo()))
                .cantidadComprada(detalleDTO.getCantidadComprada())
                .precioUnitario(detalleDTO.getPrecioUnitario())
                .subtotal(detalleDTO.getSubtotal())
                .build();
    }

    // Convertir lista de DetalleCompra a lista de DetalleCompraDTO
    public static List<DetalleCompraDTO> convertListDetalleCompra(List<DetalleCompra> detalles) {
        return detalles.stream()
                .map(MapperCompra::convertDetalleCompra)
                .collect(Collectors.toList());
    }

    // Convertir Compra a CompraDTO
    public static CompraDTO convertCompra(Compra compra) {
        return CompraDTO.builder()
                .idCompra(compra.getIdCompra())
                .usuario(MapperUsuario.convertUsuarioToDTO(compra.getUsuario()))
                .proveedor(MapperProveedor.convertProveedorDTO(compra.getProveedor()))
                .detalles(convertListDetalleCompra(compra.getDetalles()))
                .totalCompra(compra.getTotalCompra())
                .fechaCompra(compra.getFechaCompra().toString()) // Convertir LocalDateTime a String
                .build();
    }

    // Convertir CompraDTO a Compra
    public static Compra convertCompraDTO(CompraDTO compraDTO) {
        return Compra.builder()
                .idCompra(compraDTO.getIdCompra())
                .usuario(MapperUsuario.convertDTOToUsuario(compraDTO.getUsuario()))
                .proveedor(MapperProveedor.convertProveedor(compraDTO.getProveedor()))
                .detalles(compraDTO.getDetalles().stream()
                        .map(MapperCompra::convertDetalleCompraDTO)
                        .collect(Collectors.toList()))
                .totalCompra(compraDTO.getTotalCompra())
                .fechaCompra(LocalDateTime.parse(compraDTO.getFechaCompra())) // Convertir String a LocalDateTime
                .build();
    }

    // Convertir lista de Compra a lista de CompraDTO
    public static List<CompraDTO> convertListCompraDTO(List<Compra> compras) {
        return compras.stream()
                .map(MapperCompra::convertCompra)
                .collect(Collectors.toList());
    }

    // Convertir lista de CompraDTO a lista de Compra
    public static List<Compra> convertListCompra(List<CompraDTO> comprasDTO) {
        return comprasDTO.stream()
                .map(MapperCompra::convertCompraDTO)
                .collect(Collectors.toList());
    }
}
