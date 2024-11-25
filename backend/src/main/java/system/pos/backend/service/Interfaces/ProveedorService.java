package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;


public interface ProveedorService {
    ProveedorDTO buscarProveedorPorId(Long id);
    List<ProveedorDTO> obtenerTodosProveedores();
    ProveedorDTO guardarProveedor(ProveedorDTO proveedor);
    ProveedorDTO actualizarProveedor(Long id, ProveedorDTO proveedor);
    void eliminarProveedor(Long id);
}
