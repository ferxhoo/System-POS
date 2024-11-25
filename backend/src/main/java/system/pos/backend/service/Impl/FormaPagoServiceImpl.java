package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.FormaPagoRepository;
import system.pos.backend.dto.Contabilidad.FormaPagoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperFormaPago;
import system.pos.backend.model.FormaPago;
import system.pos.backend.service.Interfaces.FormaPagoService;

@Service
public class FormaPagoServiceImpl implements FormaPagoService {

    private static final String FORMA_PAGO_NO_ENCONTRADA = "Forma de pago no encontrada con el ID: ";
    private static final String FORMA_PAGO_EXISTENTE = "Ya existe una forma de pago con este nombre: ";
    private static final String FORMA_PAGO_DEFAULT = "Efectivo";

    @Autowired
    private FormaPagoRepository formaPagoRepository;

    @Override
    @Transactional
    public void inicializarFormasPagoDefeault() {
        formaPagoRepository.findByNombreFormaPagoIgnoreCase(FORMA_PAGO_DEFAULT)
                .orElseGet(() -> formaPagoRepository.save(createFormaPagoDefault()));
    }

    private FormaPago createFormaPagoDefault() {
        return FormaPago.builder()
                .nombreFormaPago(FORMA_PAGO_DEFAULT)
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public FormaPagoDTO buscarFormaPagoPorId(Long id) {
        FormaPago formaPago = formaPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORMA_PAGO_NO_ENCONTRADA + id));
        return MapperFormaPago.convertFormaPagoDTO(formaPago);
    }

    @Override
    @Transactional(readOnly = true)
    public List<FormaPagoDTO> obtenerListaFormasPago() {
        List<FormaPago> formasPago = formaPagoRepository.findAll();
        if (formasPago.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron formas de pago registradas");
        }
        return MapperFormaPago.convertListFormaPagoDTO(formasPago);
    }

    @Override
    @Transactional
    public FormaPagoDTO guardarFormaPago(FormaPagoDTO formaPagoDTO) {
        validarFormaPagoExistente(formaPagoDTO.getNombreFormaPago());
        FormaPago formaPago = MapperFormaPago.convertFormaPago(formaPagoDTO);
        return MapperFormaPago.convertFormaPagoDTO(formaPagoRepository.save(formaPago));
    }

    @Override
    @Transactional
    public FormaPagoDTO actualizarFormaPago(Long id, FormaPagoDTO formaPagoDTO) {
        FormaPago formaPagoActual = formaPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORMA_PAGO_NO_ENCONTRADA + id));

        if (!formaPagoActual.getNombreFormaPago().equalsIgnoreCase(formaPagoDTO.getNombreFormaPago())) {
            validarFormaPagoExistente(formaPagoDTO.getNombreFormaPago());
        }

        formaPagoActual.setNombreFormaPago(formaPagoDTO.getNombreFormaPago());
        return MapperFormaPago.convertFormaPagoDTO(formaPagoRepository.save(formaPagoActual));
    }

    @Override
    @Transactional
    public void eliminarFormaPago(Long id) {
        FormaPago formaPago = formaPagoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(FORMA_PAGO_NO_ENCONTRADA + id));
        formaPagoRepository.delete(formaPago);
    }

    // Métodos auxiliares
    private void validarFormaPagoExistente(String nombreFormaPago) {
        if (formaPagoRepository.existsByNombreFormaPagoIgnoreCase(nombreFormaPago)) {
            throw new ConflictException(FORMA_PAGO_EXISTENTE + nombreFormaPago);
        }
    }
}

