package system.pos.backend.service.Impl;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.ClienteRepository;
import system.pos.backend.Repository.MesaRepository;
import system.pos.backend.Repository.ReservaRepository;
import system.pos.backend.dto.Reserva.ReservaDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperReserva;
import system.pos.backend.model.Cliente;
import system.pos.backend.model.Mesa;
import system.pos.backend.model.Reserva;
import system.pos.backend.service.Interfaces.ReservaService;

@Service
public class ReservaServiceImpl implements ReservaService {

    private static final String RESERVA_NO_ENCONTRADA = "Reserva no encontrada con el ID: ";
    private static final String MESA_NO_ENCONTRADA = "Mesa no encontrada con el ID: ";
    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con el ID: ";

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private MesaRepository mesaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional(readOnly = true)
    public List<ReservaDTO> listarTodasLasReservas() {
        List<Reserva> reservas = reservaRepository.findAll();
        if (reservas.isEmpty()) {
            return Collections.emptyList();
        }
        return reservas.stream()
                .map(MapperReserva::convertReservaDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public ReservaDTO guardarReserva(ReservaDTO reservaDTO) {
        Mesa mesa = mesaRepository.findById(reservaDTO.getMesa().getIdMesa())
                .orElseThrow(() -> new ResourceNotFoundException(MESA_NO_ENCONTRADA + reservaDTO.getMesa().getIdMesa()));

        Cliente cliente;
        if (reservaDTO.getCliente().getIdCliente() != null) {
            cliente = clienteRepository.findById(reservaDTO.getCliente().getIdCliente())
                    .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO + reservaDTO.getCliente().getIdCliente()));
        } else {
            cliente = clienteRepository.findByNumeroDocumentoIgnoreCase(reservaDTO.getCliente().getNumeroDocumento())
                    .orElseGet(() -> clienteRepository.save(
                            Cliente.builder()
                                    .nombreCompleto(reservaDTO.getCliente().getNombreCompleto())
                                    .numeroDocumento(reservaDTO.getCliente().getNumeroDocumento())
                                    .build()
                    ));
        }

        if (mesa.getEstado().equalsIgnoreCase("RESERVADA")) {
            throw new ConflictException("La mesa ya está reservada");
        }

        Reserva nuevaReserva = Reserva.builder()
                .mesa(mesa)
                .cliente(cliente)
                .estado(reservaDTO.getEstado() != null ? reservaDTO.getEstado() : "EN PROCESO")
                .observaciones(reservaDTO.getObservaciones())
                .fechaReserva(reservaDTO.getFechaReserva() != null ? LocalDateTime.parse(reservaDTO.getFechaReserva()) : LocalDateTime.now())
                .build();

        mesa.setEstado("RESERVADA");
        mesaRepository.save(mesa);

        return MapperReserva.convertReservaDTO(reservaRepository.save(nuevaReserva));
    }

    @Override
    @Transactional(readOnly = true)
    public ReservaDTO obtenerReservaPorId(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVA_NO_ENCONTRADA + idReserva));
        return MapperReserva.convertReservaDTO(reserva);
    }

    @Override
    @Transactional
    public ReservaDTO actualizarReserva(Long idReserva, ReservaDTO reservaDTO) {
        Reserva reservaExistente = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVA_NO_ENCONTRADA + idReserva));

        Mesa mesa = mesaRepository.findById(reservaDTO.getMesa().getIdMesa())
                .orElseThrow(() -> new ResourceNotFoundException(MESA_NO_ENCONTRADA + reservaDTO.getMesa().getIdMesa()));

        Cliente cliente = clienteRepository.findById(reservaDTO.getCliente().getIdCliente())
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO + reservaDTO.getCliente().getIdCliente()));

        reservaExistente.setMesa(mesa);
        reservaExistente.setCliente(cliente);
        reservaExistente.setEstado(reservaDTO.getEstado());
        reservaExistente.setObservaciones(reservaDTO.getObservaciones());

        // Cambiar estado de la mesa si la reserva es cancelada
        if ("CANCELADA".equalsIgnoreCase(reservaDTO.getEstado())) {
            mesa.setEstado("LIBRE");
            mesaRepository.save(mesa);
        }

        return MapperReserva.convertReservaDTO(reservaRepository.save(reservaExistente));
    }

    @Override
    @Transactional
    public void eliminarReserva(Long idReserva) {
        Reserva reserva = reservaRepository.findById(idReserva)
                .orElseThrow(() -> new ResourceNotFoundException(RESERVA_NO_ENCONTRADA + idReserva));

        Mesa mesa = reserva.getMesa();
        if ("RESERVADA".equalsIgnoreCase(mesa.getEstado())) {
            mesa.setEstado("LIBRE");
            mesaRepository.save(mesa);
        }

        reservaRepository.delete(reserva);
    }
}
