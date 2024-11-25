package system.pos.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;
import system.pos.backend.model.Proveedor;

public class MapperProveedor {

    // Convierte un ProveedorDTO a una entidad Proveedor
    public static Proveedor convertProveedor(ProveedorDTO proveedorDTO) {
        if (proveedorDTO == null) {
            return null;
        }
        return Proveedor.builder()
                .idProveedor(proveedorDTO.getIdProveedor())
                .tipoProveedor(proveedorDTO.getTipoProveedor())
                .tipoDocumento(proveedorDTO.getTipoDocumento())
                .numeroDocumento(proveedorDTO.getNumeroDocumento())
                .nombreProveedor(proveedorDTO.getNombreProveedor())
                .direccion(proveedorDTO.getDireccion())
                .pais(proveedorDTO.getPais())
                .departamento(proveedorDTO.getDepartamento())
                .ciudad(proveedorDTO.getCiudad())
                .telefono(proveedorDTO.getTelefono())
                .email(proveedorDTO.getEmail())
                .build();
    }

    // Convierte una entidad Proveedor a un ProveedorDTO
    public static ProveedorDTO convertProveedorDTO(Proveedor proveedor) {
        if (proveedor == null) {
            return null;
        }
        return ProveedorDTO.builder()
                .idProveedor(proveedor.getIdProveedor())
                .tipoProveedor(proveedor.getTipoProveedor())
                .tipoDocumento(proveedor.getTipoDocumento())
                .numeroDocumento(proveedor.getNumeroDocumento())
                .nombreProveedor(proveedor.getNombreProveedor())
                .direccion(proveedor.getDireccion())
                .pais(proveedor.getPais())
                .departamento(proveedor.getDepartamento())
                .ciudad(proveedor.getCiudad())
                .telefono(proveedor.getTelefono())
                .email(proveedor.getEmail())
                .ultimaActualizacion(proveedor.getUltimaActualizacion() != null 
                        ? proveedor.getUltimaActualizacion().toString() 
                        : null)
                .build();
    }

    // Convierte una lista de ProveedorDTO a una lista de entidades Proveedor
    public static List<Proveedor> convertListProveedor(List<ProveedorDTO> proveedorDTOs) {
        if (proveedorDTOs == null || proveedorDTOs.isEmpty()) {
            return List.of();
        }
        return proveedorDTOs.stream()
                .map(MapperProveedor::convertProveedor)
                .collect(Collectors.toList());
    }

    // Convierte una lista de entidades Proveedor a una lista de ProveedorDTO
    public static List<ProveedorDTO> convertListProveedorDTO(List<Proveedor> proveedores) {
        if (proveedores == null || proveedores.isEmpty()) {
            return List.of();
        }
        return proveedores.stream()
                .map(MapperProveedor::convertProveedorDTO)
                .collect(Collectors.toList());
    }
}

