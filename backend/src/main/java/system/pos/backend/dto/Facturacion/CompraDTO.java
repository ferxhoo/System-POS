package system.pos.backend.dto.Facturacion;

import java.util.List;

import lombok.Builder;
import lombok.Data;
import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;

@Data
@Builder
public class CompraDTO {
    private Long idCompra;
    private UsuarioDTO usuario; // Referencia al DTO de Usuario
    private ProveedorDTO proveedor; // Referencia al DTO de Proveedor
    private List<DetalleCompraDTO> detalles; // Lista de detalles de compra
    private Double totalCompra;
    private String fechaCompra;
}