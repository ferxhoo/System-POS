package system.pos.backend.service.Impl;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.CompraRepository;
import system.pos.backend.Repository.DetalleCompraRepository;
import system.pos.backend.Repository.InsumoRepository;
import system.pos.backend.Repository.InventarioRepository;
import system.pos.backend.Repository.ProveedorRepository;
import system.pos.backend.Repository.UsuarioRepository;
import system.pos.backend.dto.Facturacion.CompraDTO;
import system.pos.backend.dto.Facturacion.DetalleCompraDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperCompra;
import system.pos.backend.model.Compra;
import system.pos.backend.model.DetalleCompra;
import system.pos.backend.model.Insumo;
import system.pos.backend.model.Inventario;
import system.pos.backend.model.Proveedor;
import system.pos.backend.model.Usuario;
import system.pos.backend.service.Interfaces.CompraService;

@Service
public class CompraServiceImpl implements CompraService {

    @Autowired
    private CompraRepository compraRepository;

    @Autowired
    private DetalleCompraRepository detalleCompraRepository;

    @Autowired
    private InsumoRepository insumoRepository;

    @Autowired
    private InventarioRepository inventarioRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ProveedorRepository proveedorRepository;

    // Registrar una compra
    @Override
    @Transactional
    public CompraDTO registrarCompra(CompraDTO compraDTO) {
        Usuario usuario = usuarioRepository.findById(compraDTO.getUsuario().getIdUsuario())
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado con ID: " + compraDTO.getUsuario().getIdUsuario()));

        Proveedor proveedor = proveedorRepository.findById(compraDTO.getProveedor().getIdProveedor())
                .orElseThrow(() -> new ResourceNotFoundException("Proveedor no encontrado con ID: " + compraDTO.getProveedor().getIdProveedor()));

        // Crear la compra
        Compra compra = Compra.builder()
                .usuario(usuario)
                .proveedor(proveedor)
                .totalCompra(compraDTO.getTotalCompra())
                .fechaCompra(LocalDateTime.parse(compraDTO.getFechaCompra()))
                .build();

        // Guardar la compra
        Compra compraGuardada = compraRepository.save(compra);

        // Procesar los detalles de la compra
        List<DetalleCompra> detalles = new ArrayList<>();
        for (DetalleCompraDTO detalleDTO : compraDTO.getDetalles()) {
            Insumo insumo = insumoRepository.findById(detalleDTO.getInsumo().getIdInsumo())
                    .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + detalleDTO.getInsumo().getIdInsumo()));

            // Incrementar inventario
            Inventario inventario = inventarioRepository.findByInsumo_Id(insumo.getId());
            if (inventario == null) {
                inventario = Inventario.builder()
                        .insumo(insumo)
                        .cantidadDisponible(0)
                        .build();
            }
            inventario.setCantidadDisponible(inventario.getCantidadDisponible() + detalleDTO.getCantidadComprada());
            inventarioRepository.save(inventario);

            // Crear detalle
            DetalleCompra detalle = DetalleCompra.builder()
                    .compra(compraGuardada)
                    .insumo(insumo)
                    .cantidadComprada(detalleDTO.getCantidadComprada())
                    .precioUnitario(detalleDTO.getPrecioUnitario())
                    .subtotal(detalleDTO.getSubtotal())
                    .build();

            detalles.add(detalle);
        }

        // Guardar detalles en cascada
        detalleCompraRepository.saveAll(detalles);

        // Asignar detalles a la compra y actualizar
        compraGuardada.setDetalles(detalles);
        compraRepository.save(compraGuardada);

        return MapperCompra.convertCompra(compraGuardada);
    }

    // Listar todas las compras
    @Override
    public List<CompraDTO> listarCompras() {
        List<Compra> compras = compraRepository.findAll();
        return MapperCompra.convertListCompraDTO(compras);
    }

    // Buscar compra por ID
    @Override
    public CompraDTO buscarCompraPorId(Long idCompra) {
        Compra compra = compraRepository.findById(idCompra)
                .orElseThrow(() -> new ResourceNotFoundException("Compra no encontrada con ID: " + idCompra));
        return MapperCompra.convertCompra(compra);
    }
}
