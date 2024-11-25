package system.pos.backend.service.Interfaces;

import system.pos.backend.dto.Negocio.NegocioDTO;

public interface NegocioService {
    NegocioDTO guardarNegocio(NegocioDTO negocioDTO);
    NegocioDTO buscarPorNit(String nit);
    NegocioDTO actualizarNegocio(String nit, NegocioDTO negocioDTO);
    void eliminarNegocio(String nit);
}

