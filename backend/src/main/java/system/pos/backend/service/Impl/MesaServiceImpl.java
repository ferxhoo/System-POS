package system.pos.backend.service.Impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.MesaRepository;
import system.pos.backend.dto.Reserva.MesaDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperMesa;
import system.pos.backend.model.Mesa;
import system.pos.backend.service.Interfaces.MesaService;

@Service
public class MesaServiceImpl implements MesaService {

    private static final String MESA_NO_ENCONTRADA = "Mesa no encontrada con ID: ";

    @Autowired
    private MesaRepository mesaRepository;

    @Override
    @Transactional(readOnly = true)
    public MesaDTO buscarMesaPorId(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESA_NO_ENCONTRADA + id));
        return MapperMesa.convertMesaDTO(mesa);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MesaDTO> obtenerTodasLasMesas() {
        List<Mesa> mesas = mesaRepository.findAll();
        if (mesas.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron mesas registradas");
        }
        return MapperMesa.convertListMesaDTO(mesas);
    }

    @Override
    @Transactional
    public MesaDTO guardarMesa(MesaDTO mesaDTO) {
        Mesa mesa = MapperMesa.convertMesa(mesaDTO);
        if (mesa.getEstado() == null) {
            mesa.setEstado("Libre");
        }
        return MapperMesa.convertMesaDTO(mesaRepository.save(mesa));
    }

    @Override
    @Transactional
    public MesaDTO actualizarMesa(Long id, MesaDTO mesaDTO) {
        Mesa mesaActual = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESA_NO_ENCONTRADA + id));

        mesaActual = actualizarDatosMesa(mesaActual, mesaDTO);
        return MapperMesa.convertMesaDTO(mesaRepository.save(mesaActual));
    }

    @Override
    @Transactional
    public void eliminarMesa(Long id) {
        Mesa mesa = mesaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(MESA_NO_ENCONTRADA + id));
        mesaRepository.delete(mesa);
    }

    @Override
    @Transactional
    public List<MesaDTO> crearVariasMesas(int cantidad) {
        if (cantidad <= 0) {
            throw new IllegalArgumentException("La cantidad debe ser mayor a cero");
        }

        List<Mesa> nuevasMesas = new ArrayList<>();
        for (int i = 1; i <= cantidad; i++) {
            Mesa mesa = Mesa.builder()
                    .nombreMesa("Mesa #" + i)
                    .estado("Libre")
                    .build();
            nuevasMesas.add(mesa);
        }

        List<Mesa> mesasGuardadas = mesaRepository.saveAll(nuevasMesas);
        return MapperMesa.convertListMesaDTO(mesasGuardadas);
    }

    // Métodos auxiliares
    private Mesa actualizarDatosMesa(Mesa mesaActual, MesaDTO mesaDTO) {
        Mesa.MesaBuilder mesaBuilder = mesaActual.toBuilder()
                .nombreMesa(mesaDTO.getNombreMesa());

        if (mesaDTO.getEstado() != null) {
            mesaBuilder.estado(mesaDTO.getEstado());
        }

        return mesaBuilder.build();
    }
}
