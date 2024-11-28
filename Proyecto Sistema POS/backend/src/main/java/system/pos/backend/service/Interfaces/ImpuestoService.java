package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Inventario.ImpuestoDTO;

import java.util.List;

public interface ImpuestoService {
    void inicializarImpuestosDefeault();
    ImpuestoDTO buscarImpuestoPorId(Long id);
    List<ImpuestoDTO> obtenerListaImpuestos();
    ImpuestoDTO guardarImpuesto(ImpuestoDTO impuesto);
    ImpuestoDTO actualizarImpuesto(Long id, ImpuestoDTO impuesto);
    void eliminarImpuesto(Long id);
}
