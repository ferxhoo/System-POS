package system.pos.backend.service.Impl;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.ImpuestoRepository;
import system.pos.backend.dto.Inventario.ImpuestoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperImpuesto;
import system.pos.backend.model.Impuesto;
import system.pos.backend.service.Interfaces.ImpuestoService;

@Service
public class ImpuestoServiceImpl implements ImpuestoService {

    private static final String IMPUESTO_NO_ENCONTRADO = "Impuesto no encontrado con el ID: ";
    private static final String IMPUESTO_EXISTENTE = "Ya existe un impuesto con esta tarifa y concepto: ";

    @Autowired
    private ImpuestoRepository impuestoRepository;

    @Override
    @Transactional
    public void inicializarImpuestosDefeault() {
        List<Impuesto> impuestosPorDefecto = List.of(
            Impuesto.builder().nombreImpuesto("IVA").concepto("Excento 0%").tarifa(new BigDecimal("0.00")).build(),
            Impuesto.builder().nombreImpuesto("IVA").concepto("Tarifas 5%").tarifa(new BigDecimal("0.05")).build(),
            Impuesto.builder().nombreImpuesto("IVA").concepto("Tarifas 16%").tarifa(new BigDecimal("0.16")).build(),
            Impuesto.builder().nombreImpuesto("IVA").concepto("Tarifas 19%").tarifa(new BigDecimal("0.19")).build(),
            Impuesto.builder().nombreImpuesto("INC").concepto("Tarifas 2%").tarifa(new BigDecimal("0.02")).build(),
            Impuesto.builder().nombreImpuesto("INC").concepto("Tarifas 4%").tarifa(new BigDecimal("0.04")).build(),
            Impuesto.builder().nombreImpuesto("INC").concepto("Tarifas 8%").tarifa(new BigDecimal("0.08")).build(),
            Impuesto.builder().nombreImpuesto("INC").concepto("Tarifas 16%").tarifa(new BigDecimal("0.16")).build(),
            Impuesto.builder().nombreImpuesto("ICUI").concepto("Tarifas 10%").tarifa(new BigDecimal("0.10")).build(),
            Impuesto.builder().nombreImpuesto("ICUI").concepto("Tarifas 15%").tarifa(new BigDecimal("0.15")).build()
        );

        impuestosPorDefecto.forEach(impuesto -> {
            if (!impuestoRepository.existsByNombreImpuestoAndTarifa(impuesto.getNombreImpuesto(), impuesto.getTarifa())) {
                impuestoRepository.save(impuesto);
            }
        });
    }

    @Override
    @Transactional(readOnly = true)
    public ImpuestoDTO buscarImpuestoPorId(Long id) {
        Impuesto impuesto = impuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IMPUESTO_NO_ENCONTRADO + id));
        return MapperImpuesto.convertImpuestoDTO(impuesto);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ImpuestoDTO> obtenerListaImpuestos() {
        List<Impuesto> impuestos = impuestoRepository.findAll();
        if (impuestos.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron impuestos registrados");
        }
        return MapperImpuesto.convertListImpuestoDTO(impuestos);
    }

    @Override
    @Transactional
    public ImpuestoDTO guardarImpuesto(ImpuestoDTO impuestoDTO) {
        BigDecimal tarifa = new BigDecimal(impuestoDTO.getTarifa());
        validarImpuestoExistente(impuestoDTO.getNombreImpuesto(), tarifa);

        Impuesto impuesto = MapperImpuesto.convertImpuesto(impuestoDTO);
        return MapperImpuesto.convertImpuestoDTO(impuestoRepository.save(impuesto));
    }

    @Override
    @Transactional
    public ImpuestoDTO actualizarImpuesto(Long id, ImpuestoDTO impuestoDTO) {
        Impuesto impuestoActual = impuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IMPUESTO_NO_ENCONTRADO + id));

        BigDecimal tarifa = new BigDecimal(impuestoDTO.getTarifa());

        if (!impuestoActual.getNombreImpuesto().equals(impuestoDTO.getNombreImpuesto())
                || !impuestoActual.getTarifa().equals(tarifa)) {
            validarImpuestoExistente(impuestoDTO.getNombreImpuesto(), tarifa);
        }

        impuestoActual = actualizarDatosImpuesto(impuestoActual, impuestoDTO);
        return MapperImpuesto.convertImpuestoDTO(impuestoRepository.save(impuestoActual));
    }

    @Override
    @Transactional
    public void eliminarImpuesto(Long id) {
        Impuesto impuesto = impuestoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(IMPUESTO_NO_ENCONTRADO + id));
        impuestoRepository.delete(impuesto);
    }

    // Métodos auxiliares
    private void validarImpuestoExistente(String nombreImpuesto, BigDecimal tarifa) {
        if (impuestoRepository.existsByNombreImpuestoAndTarifa(nombreImpuesto, tarifa)) {
            throw new ConflictException(IMPUESTO_EXISTENTE + nombreImpuesto + " con tarifa " + tarifa);
        }
    }

    private Impuesto actualizarDatosImpuesto(Impuesto impuestoActual, ImpuestoDTO impuestoDTO) {
        BigDecimal tarifa = new BigDecimal(impuestoDTO.getTarifa());
        return impuestoActual.toBuilder()
                .nombreImpuesto(impuestoDTO.getNombreImpuesto())
                .concepto(impuestoDTO.getConcepto())
                .tarifa(tarifa)
                .build();
    }
}
