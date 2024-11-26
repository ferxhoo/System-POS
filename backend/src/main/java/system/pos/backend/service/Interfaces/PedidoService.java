package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.Pedido.PedidoDTO;

public interface PedidoService {
    PedidoDTO crearPedido(PedidoDTO pedidoDTO);
    List<PedidoDTO> listarPedidos();
    PedidoDTO buscarPedidoPorId(Long idPedido);
    PedidoDTO actualizarEstadoPedido(Long idPedido, String nuevoEstado);
    void cancelarPedido(Long idPedido);
}


