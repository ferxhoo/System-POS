package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.pos.backend.Repository.InventarioRepository;
import system.pos.backend.dto.Inventario.InventarioDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperInventario;
import system.pos.backend.model.Inventario;
import system.pos.backend.service.Interfaces.InventarioService;

@Service
public class InventarioServiceImpl implements InventarioService {

    @Autowired
    private InventarioRepository inventarioRepository;

    // Sumar cantidad al inventario
    @Override
    public InventarioDTO sumarCantidad(Long idInsumo, Integer cantidad) {
        Inventario inventario = inventarioRepository.findByInsumo_Id(idInsumo);
        if (inventario == null) {
            throw new ResourceNotFoundException("No se encontró inventario para el insumo con ID: " + idInsumo);
        }
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() + cantidad);
        Inventario inventarioActualizado = inventarioRepository.save(inventario);
        return MapperInventario.convertInventarioDTO(inventarioActualizado);
    }

    // Restar cantidad del inventario
    @Override
    public InventarioDTO restarCantidad(Long idInsumo, Integer cantidad) {
        Inventario inventario = inventarioRepository.findByInsumo_Id(idInsumo);
        if (inventario == null) {
            throw new ResourceNotFoundException("No se encontró inventario para el insumo con ID: " + idInsumo);
        }
        if (inventario.getCantidadDisponible() < cantidad) {
            throw new ConflictException("No hay suficiente stock en el inventario para restar " + cantidad + " unidades");
        }
        inventario.setCantidadDisponible(inventario.getCantidadDisponible() - cantidad);
        Inventario inventarioActualizado = inventarioRepository.save(inventario);
        return MapperInventario.convertInventarioDTO(inventarioActualizado);
    }

    // Listar todo el inventario
    @Override
    public List<InventarioDTO> listarInventario() {
        List<Inventario> inventarioList = inventarioRepository.findAll();
        return MapperInventario.convertListInventarioDTO(inventarioList);
    }
}

