package system.pos.backend.service.Impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.NegocioRepository;
import system.pos.backend.dto.Negocio.NegocioDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperNegocio;
import system.pos.backend.model.Negocio;
import system.pos.backend.service.Interfaces.NegocioService;

@Service
public class NegocioServiceImpl implements NegocioService {

    private static final String NEGOCIO_NO_ENCONTRADO = "Negocio no encontrado con el NIT: ";

    @Autowired
    private NegocioRepository negocioRepository;

    @Override
    @Transactional
    public NegocioDTO guardarNegocio(NegocioDTO negocioDTO) {
        Negocio negocio = MapperNegocio.convertDTOToNegocio(negocioDTO);
        Negocio negocioGuardado = negocioRepository.save(negocio);
        return MapperNegocio.convertNegocioToDTO(negocioGuardado);
    }

    @Override
    @Transactional(readOnly = true)
    public NegocioDTO buscarPorNit(String nit) {
        Negocio negocio = negocioRepository.findById(nit)
                .orElseThrow(() -> new ResourceNotFoundException(NEGOCIO_NO_ENCONTRADO + nit));
        return MapperNegocio.convertNegocioToDTO(negocio);
    }

    @Override
    @Transactional
    public NegocioDTO actualizarNegocio(String nit, NegocioDTO negocioDTO) {
        Negocio negocioExistente = negocioRepository.findById(nit)
                .orElseThrow(() -> new ResourceNotFoundException(NEGOCIO_NO_ENCONTRADO + nit));

        negocioExistente = negocioExistente.toBuilder()
                .nombreNegocio(negocioDTO.getNombreNegocio())
                .direccion(negocioDTO.getDireccion())
                .pais(negocioDTO.getPais())
                .departamento(negocioDTO.getDepartamento())
                .ciudad(negocioDTO.getCiudad())
                .telefono(negocioDTO.getTelefono())
                .email(negocioDTO.getEmail())
                .representanteLegal(negocioDTO.getRepresentanteLegal())
                .tipoRegimen(negocioDTO.getTipoRegimen())
                .responsabilidadFiscal(negocioDTO.getResponsabilidadFiscal())
                .build();

        Negocio negocioActualizado = negocioRepository.save(negocioExistente);

        return MapperNegocio.convertNegocioToDTO(negocioActualizado);
    }

    @Override
    @Transactional
    public void eliminarNegocio(String nit) {
        Negocio negocio = negocioRepository.findById(nit)
                .orElseThrow(() -> new ResourceNotFoundException(NEGOCIO_NO_ENCONTRADO + nit));

        negocioRepository.delete(negocio);
    }
}
