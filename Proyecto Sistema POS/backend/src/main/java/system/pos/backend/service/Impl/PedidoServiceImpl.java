package system.pos.backend.service.Impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.ClienteRepository;
import system.pos.backend.Repository.DetallePedidoRepository;
import system.pos.backend.Repository.MesaRepository;
import system.pos.backend.Repository.PedidoRepository;
import system.pos.backend.Repository.ProductoRepository;
import system.pos.backend.Repository.RecetaRepository;
import system.pos.backend.Repository.UsuarioRepository;
import system.pos.backend.dto.Pedido.DetallePedidoDTO;
import system.pos.backend.dto.Pedido.PedidoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperCliente;
import system.pos.backend.mapper.MapperPedido;
import system.pos.backend.model.Cliente;
import system.pos.backend.model.DetallePedido;
import system.pos.backend.model.Mesa;
import system.pos.backend.model.Pedido;
import system.pos.backend.model.Producto;
import system.pos.backend.model.Receta;
import system.pos.backend.model.Usuario;
import system.pos.backend.service.Interfaces.InventarioService;
import system.pos.backend.service.Interfaces.PedidoService;
@Service
public class PedidoServiceImpl implements PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private DetallePedidoRepository detallePedidoRepository;
    @Autowired
    private MesaRepository mesaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private ProductoRepository productoRepository;
    @Autowired
    private InventarioService inventarioService;
    @Autowired
    private RecetaRepository recetaRepository;

    // Crear un pedido
    @Override
    @Transactional
    public PedidoDTO crearPedido(PedidoDTO pedidoDTO) {
        Mesa mesa = mesaRepository.findById(pedidoDTO.getMesa().getIdMesa())
                .orElseThrow(() -> new ResourceNotFoundException("Mesa no encontrada con ID: " + pedidoDTO.getMesa().getIdMesa()));

        Usuario usuario = usuarioRepository.findById(pedidoDTO.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + pedidoDTO.getUsuario().getIdUsuario()));

        Cliente cliente = null;
        if (pedidoDTO.getCliente() != null) {
            if (pedidoDTO.getCliente().getIdCliente() != null) {
                cliente = clienteRepository.findById(pedidoDTO.getCliente().getIdCliente())
                        .orElseThrow(() -> new ResourceNotFoundException("Cliente no encontrado con ID: " + pedidoDTO.getCliente().getIdCliente()));
            } else {
                cliente = clienteRepository.save(MapperCliente.convertCliente(pedidoDTO.getCliente())); // Crear nuevo cliente
            }
        }

        if (pedidoDTO.getDetalles() == null || pedidoDTO.getDetalles().isEmpty()) {
            throw new ConflictException("El pedido debe contener al menos un detalle");
        }

        Pedido pedido = Pedido.builder()
                .mesa(mesa)
                .usuario(usuario)
                .cliente(cliente)
                .estado("Pendiente")
                .build();

        mesa.setEstado("Ocupada"); // Cambiar el estado de la mesa
        mesaRepository.save(mesa);

        Pedido pedidoGuardado = pedidoRepository.save(pedido);

        List<DetallePedido> detalles = new ArrayList<>();
        for (DetallePedidoDTO detalleDTO : pedidoDTO.getDetalles()) {
            Producto producto = productoRepository.findById(detalleDTO.getProducto().getIdProducto())
                    .orElseThrow(() -> new ResourceNotFoundException("Producto no encontrado con ID: " + detalleDTO.getProducto().getIdProducto()));

            DetallePedido detallePedido = DetallePedido.builder()
                    .pedido(pedidoGuardado)
                    .producto(producto)
                    .cantidad(detalleDTO.getCantidad())
                    .subtotal(new BigDecimal(detalleDTO.getSubtotal()))
                    .build();

            detalles.add(detallePedido);
        }

        detallePedidoRepository.saveAll(detalles);
        pedidoGuardado.setDetalles(detalles);

        return MapperPedido.convertPedidoDTO(pedidoGuardado);
    }

    // Listar todos los pedidos
    @Override
    public List<PedidoDTO> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        return MapperPedido.convertListPedidoDTO(pedidos);
    }

    // Buscar pedido por ID
    @Override
    public PedidoDTO buscarPedidoPorId(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + idPedido));
        return MapperPedido.convertPedidoDTO(pedido);
    }

    // Actualizar estado del pedido
    @Override
    @Transactional
    public PedidoDTO actualizarEstadoPedido(Long idPedido, String nuevoEstado) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + idPedido));

        if (nuevoEstado.equalsIgnoreCase(pedido.getEstado())) {
            throw new ConflictException("El pedido ya está en el estado: " + nuevoEstado);
        }

        if ("Confirmado".equalsIgnoreCase(nuevoEstado)) {
            for (DetallePedido detalle : pedido.getDetalles()) {
                Producto producto = detalle.getProducto();

                List<Receta> recetas = recetaRepository.findByProducto_IdProducto(producto.getIdProducto());
                for (Receta receta : recetas) {
                    double cantidadTotal = receta.getCantidadIngrediente() * detalle.getCantidad();
                    inventarioService.restarCantidad(receta.getInsumo().getId(), (int) Math.ceil(cantidadTotal));
                }
            }
        }

        pedido.setEstado(nuevoEstado);
        Pedido pedidoActualizado = pedidoRepository.save(pedido);
        return MapperPedido.convertPedidoDTO(pedidoActualizado);
    }

    // Cancelar un pedido
    @Override
    @Transactional
    public void cancelarPedido(Long idPedido) {
        Pedido pedido = pedidoRepository.findById(idPedido)
                .orElseThrow(() -> new ResourceNotFoundException("Pedido no encontrado con ID: " + idPedido));

        if ("Servido".equalsIgnoreCase(pedido.getEstado()) || "Consumido".equalsIgnoreCase(pedido.getEstado())) {
            throw new ConflictException("No se puede cancelar un pedido que ya fue servido o consumido");
        }

        pedido.setEstado("Cancelado");
        pedidoRepository.save(pedido);
    }
}
