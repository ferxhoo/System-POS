package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Contabilidad.FormaPagoDTO;

import java.util.List;

public interface FormaPagoService {
    void inicializarFormasPagoDefeault();
    FormaPagoDTO buscarFormaPagoPorId(Long id);
    List<FormaPagoDTO> obtenerListaFormasPago();
    FormaPagoDTO guardarFormaPago(FormaPagoDTO formaPago);
    FormaPagoDTO actualizarFormaPago(Long id, FormaPagoDTO formaPago);
    void eliminarFormaPago(Long id);
}
