package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.CategoriaRepository;
import system.pos.backend.Repository.InsumoRepository;
import system.pos.backend.Repository.InventarioRepository;
import system.pos.backend.Repository.UnidadRepository;
import system.pos.backend.dto.Inventario.InsumoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperInsumo;
import system.pos.backend.model.Categoria;
import system.pos.backend.model.Insumo;
import system.pos.backend.model.Inventario;
import system.pos.backend.model.Unidad;
import system.pos.backend.service.Interfaces.InsumoService;

@Service
public class InsumoServiceImpl implements InsumoService {

    @Autowired
    private InsumoRepository insumoRepository;
    @Autowired
    private InventarioRepository inventarioRepository;
    @Autowired
    private CategoriaRepository categoriaRepository;
    @Autowired
    private UnidadRepository unidadRepository;

    // Crear un insumo e inicializar su inventario en 0
    public InsumoDTO crearInsumo(InsumoDTO insumoDTO) {
        Categoria categoria = categoriaRepository.findById(insumoDTO.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + insumoDTO.getCategoria().getIdCategoria()));

        Unidad unidad = unidadRepository.findById(insumoDTO.getUnidad().getIdUnidad())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + insumoDTO.getUnidad().getIdUnidad()));
        Insumo insumo = MapperInsumo.convertInsumo(insumoDTO);
        insumo.setCategoria(categoria);
        insumo.setUnidad(unidad);
        Insumo insumoGuardado = insumoRepository.save(insumo);
        Inventario inventario = new Inventario();
        inventario.setInsumo(insumoGuardado);
        inventario.setCantidadDisponible(0);
        inventarioRepository.save(inventario);
        return MapperInsumo.convertInsumoDTO(insumoGuardado);
    }

    // Buscar un insumo por ID
    public InsumoDTO buscarInsumoPorId(Long id) {
        Insumo insumo = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        return MapperInsumo.convertInsumoDTO(insumo);
    }

    // Editar un insumo
    public InsumoDTO editarInsumo(Long id, InsumoDTO insumoDTO) {
        Insumo insumoExistente = insumoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Insumo no encontrado con ID: " + id));
        Categoria categoria = categoriaRepository.findById(insumoDTO.getCategoria().getIdCategoria())
                .orElseThrow(() -> new ResourceNotFoundException("Categoria no encontrada con ID: " + insumoDTO.getCategoria().getIdCategoria()));
        Unidad unidad = unidadRepository.findById(insumoDTO.getUnidad().getIdUnidad())
                .orElseThrow(() -> new ResourceNotFoundException("Unidad no encontrada con ID: " + insumoDTO.getUnidad().getIdUnidad()));
        insumoExistente.setNombreInsumo(insumoDTO.getNombreInsumo());
        insumoExistente.setCategoria(categoria);
        insumoExistente.setUnidad(unidad);
        Insumo insumoActualizado = insumoRepository.save(insumoExistente);
        return MapperInsumo.convertInsumoDTO(insumoActualizado);
    }

    @Override
    @Transactional
    public void eliminarInsumo(Long id) {
        if (!insumoRepository.existsById(id)) {
            throw new ResourceNotFoundException("Insumo no encontrado con ID: " + id);
        }
    
        Inventario inventario = inventarioRepository.findByInsumo_Id(id);
        if (inventario != null && inventario.getCantidadDisponible() > 0) {
            throw new ConflictException("No se puede eliminar el insumo porque tiene inventarios asignados");
        }
    
        if (inventario != null) {
            inventarioRepository.deleteByInsumo_Id(id);
        }
        insumoRepository.deleteById(id);
    }

    // Listar todos los insumos
    public List<InsumoDTO> listarInsumos() {
        List<Insumo> insumos = insumoRepository.findAll();
        return MapperInsumo.convertListInsumoDTO(insumos);
    }
}

