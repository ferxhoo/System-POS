package system.pos.backend.service.Interfaces;

import java.util.List;
import system.pos.backend.dto.Facturacion.CompraDTO;

public interface CompraService {
    CompraDTO registrarCompra(CompraDTO compraDTO);
    List<CompraDTO> listarCompras();
    CompraDTO buscarCompraPorId(Long id);
}
