package system.pos.backend.mapper;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.Pedido.DetallePedidoDTO;
import system.pos.backend.dto.Pedido.PedidoDTO;
import system.pos.backend.model.DetallePedido;
import system.pos.backend.model.Pedido;

public class MapperPedido {

    // Conversión de Pedido a PedidoDTO
    public static PedidoDTO convertPedidoDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }

        return PedidoDTO.builder()
                .idPedido(pedido.getIdPedido())
                .mesa(MapperMesa.convertMesaDTO(pedido.getMesa()))
                .usuario(MapperUsuario.convertUsuarioToDTO(pedido.getUsuario()))
                .cliente(MapperCliente.convertClienteDTO(pedido.getCliente()))
                .fechaPedido(pedido.getFechaPedido() != null ? pedido.getFechaPedido().toString() : null)
                .ultimaActualizacion(pedido.getUltimaActualizacion() != null ? pedido.getUltimaActualizacion().toString() : null)
                .estado(pedido.getEstado())
                .detalles(pedido.getDetalles() != null 
                    ? convertListDetallePedidoDTO(pedido.getDetalles())
                    : null)
                .build();
    }

    // Conversión de PedidoDTO a Pedido
    public static Pedido convertPedido(PedidoDTO pedidoDTO) {
        if (pedidoDTO == null) {
            return null;
        }

        Pedido pedido = Pedido.builder()
                .idPedido(pedidoDTO.getIdPedido())
                .mesa(MapperMesa.convertMesa(pedidoDTO.getMesa()))
                .usuario(MapperUsuario.convertDTOToUsuario(pedidoDTO.getUsuario()))
                .cliente(MapperCliente.convertCliente(pedidoDTO.getCliente()))
                .estado(pedidoDTO.getEstado())
                .build();

        if (pedidoDTO.getDetalles() != null) {
            List<DetallePedido> detalles = convertListDetallePedido(pedidoDTO.getDetalles());
            detalles.forEach(detalle -> detalle.setPedido(pedido)); // Relación bidireccional
            pedido.setDetalles(detalles);
        }

        return pedido;
    }

    // Conversión de lista de Pedido a lista de PedidoDTO
    public static List<PedidoDTO> convertListPedidoDTO(List<Pedido> pedidos) {
        if (pedidos == null) {
            return null;
        }
        return pedidos.stream()
                .map(MapperPedido::convertPedidoDTO)
                .collect(Collectors.toList());
    }

    // Conversión de lista de PedidoDTO a lista de Pedido
    public static List<Pedido> convertListPedido(List<PedidoDTO> pedidoDTOs) {
        if (pedidoDTOs == null) {
            return null;
        }
        return pedidoDTOs.stream()
                .map(MapperPedido::convertPedido)
                .collect(Collectors.toList());
    }

    // Conversión de lista de DetallePedido a lista de DetallePedidoDTO
    public static List<DetallePedidoDTO> convertListDetallePedidoDTO(List<DetallePedido> detalles) {
        if (detalles == null) {
            return null;
        }
        return detalles.stream()
                .map(MapperPedido::convertDetallePedidoDTO)
                .collect(Collectors.toList());
    }

    // Conversión de lista de DetallePedidoDTO a lista de DetallePedido
    public static List<DetallePedido> convertListDetallePedido(List<DetallePedidoDTO> detalleDTOs) {
        if (detalleDTOs == null) {
            return null;
        }
        return detalleDTOs.stream()
                .map(MapperPedido::convertDetallePedido)
                .collect(Collectors.toList());
    }

    // Conversión de DetallePedido a DetallePedidoDTO
    private static DetallePedidoDTO convertDetallePedidoDTO(DetallePedido detalle) {
        if (detalle == null) {
            return null;
        }

        return DetallePedidoDTO.builder()
                .idDetallePedido(detalle.getIdDetallePedido())
                .producto(MapperProducto.convertProductoDTO(detalle.getProducto()))
                .cantidad(detalle.getCantidad())
                .subtotal(detalle.getSubtotal() != null ? detalle.getSubtotal().toString() : null)
                .fechaDetallePedido(detalle.getFechaDetallePedido() != null ? detalle.getFechaDetallePedido().toString() : null)
                .ultimaActualizacion(detalle.getUltimaActualizacion() != null ? detalle.getUltimaActualizacion().toString() : null)
                .build();
    }

    // Conversión de DetallePedidoDTO a DetallePedido
    private static DetallePedido convertDetallePedido(DetallePedidoDTO detalleDTO) {
        if (detalleDTO == null) {
            return null;
        }

        return DetallePedido.builder()
                .idDetallePedido(detalleDTO.getIdDetallePedido())
                .producto(MapperProducto.convertProducto(detalleDTO.getProducto()))
                .cantidad(detalleDTO.getCantidad())
                .subtotal(detalleDTO.getSubtotal() != null ? new BigDecimal(detalleDTO.getSubtotal()) : null)
                .build();
    }
}

