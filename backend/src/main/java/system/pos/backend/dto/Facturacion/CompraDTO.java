package system.pos.backend.dto.Facturacion;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import system.pos.backend.dto.Contabilidad.FormaPagoDTO;
import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CompraDTO {
    private Long idCompra;
    private String numeroDocumento;
    private String fechaCompra;  
    private UsuarioDTO usuario;
    private ProveedorDTO proveedor;
    private String valorTotal;  
    private FormaPagoDTO formaPago;
}
