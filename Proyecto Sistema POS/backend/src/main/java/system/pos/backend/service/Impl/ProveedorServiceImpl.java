package system.pos.backend.service.Impl;

import system.pos.backend.Repository.ProveedorRepository;
import system.pos.backend.dto.StakeHolderExternos.ProveedorDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperProveedor;
import system.pos.backend.model.Proveedor;
import system.pos.backend.service.Interfaces.ProveedorService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProveedorServiceImpl implements ProveedorService {

    private static final String PROVEEDOR_NO_ENCONTRADO = "Proveedor no encontrado con el ID: ";
    private static final String PROVEEDOR_EXISTENTE = "Ya existe un proveedor con este número de documento: ";

    @Autowired
    private ProveedorRepository proveedorRepository;

    @Override
    @Transactional(readOnly = true)
    public ProveedorDTO buscarProveedorPorId(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVEEDOR_NO_ENCONTRADO + id));
        return MapperProveedor.convertProveedorDTO(proveedor);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ProveedorDTO> obtenerTodosProveedores() {
        List<Proveedor> proveedores = proveedorRepository.findAll();
        return proveedores.isEmpty() ? Collections.emptyList() : MapperProveedor.convertListProveedorDTO(proveedores);
    }

    @Override
    @Transactional
    public ProveedorDTO guardarProveedor(ProveedorDTO proveedorGuardar) {
        validarProveedorExistente(proveedorGuardar.getNumeroDocumento());
        Proveedor nuevoProveedor = MapperProveedor.convertProveedor(proveedorGuardar);
        return MapperProveedor.convertProveedorDTO(proveedorRepository.save(nuevoProveedor));
    }

    @Override
    @Transactional
    public ProveedorDTO actualizarProveedor(Long id, ProveedorDTO proveedorActualizado) {
        Proveedor proveedorActual = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVEEDOR_NO_ENCONTRADO + id));

        if (!proveedorActual.getNumeroDocumento().equals(proveedorActualizado.getNumeroDocumento())) {
            validarProveedorExistente(proveedorActualizado.getNumeroDocumento());
        }

        proveedorActual = actualizarDatosProveedor(proveedorActual, proveedorActualizado);
        return MapperProveedor.convertProveedorDTO(proveedorRepository.save(proveedorActual));
    }

    @Override
    public void eliminarProveedor(Long id) {
        Proveedor proveedor = proveedorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(PROVEEDOR_NO_ENCONTRADO + id));
        proveedorRepository.delete(proveedor);
    }

    // Métodos auxiliares
    private void validarProveedorExistente(String numeroDocumento) {
        if (proveedorRepository.existsByNumeroDocumentoIgnoreCase(numeroDocumento)) {
            throw new ConflictException(PROVEEDOR_EXISTENTE + numeroDocumento);
        }
    }

    private Proveedor actualizarDatosProveedor(Proveedor proveedorActual, ProveedorDTO proveedorActualizado) {
        return proveedorActual.toBuilder()
                .tipoProveedor(proveedorActualizado.getTipoProveedor())
                .tipoDocumento(proveedorActualizado.getTipoDocumento())
                .numeroDocumento(proveedorActualizado.getNumeroDocumento())
                .nombreProveedor(proveedorActualizado.getNombreProveedor())
                .direccion(proveedorActualizado.getDireccion())
                .pais(proveedorActualizado.getPais())
                .departamento(proveedorActualizado.getDepartamento())
                .ciudad(proveedorActualizado.getCiudad())
                .telefono(proveedorActualizado.getTelefono())
                .email(proveedorActualizado.getEmail())
                .build();
    }
}
