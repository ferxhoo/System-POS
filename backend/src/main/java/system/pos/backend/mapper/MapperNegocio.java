package system.pos.backend.mapper;

import java.time.LocalDateTime;

import system.pos.backend.dto.Negocio.NegocioDTO;
import system.pos.backend.model.Negocio;

public class MapperNegocio {

    // Convierte de Negocio a NegocioDTO
    public static NegocioDTO convertNegocioToDTO(Negocio negocio) {
        if (negocio == null) {
            return null;
        }
        return NegocioDTO.builder()
                .nit(negocio.getNit())
                .nombreNegocio(negocio.getNombreNegocio())
                .direccion(negocio.getDireccion())
                .pais(negocio.getPais())
                .departamento(negocio.getDepartamento())
                .ciudad(negocio.getCiudad())
                .telefono(negocio.getTelefono())
                .email(negocio.getEmail())
                .representanteLegal(negocio.getRepresentanteLegal())
                .tipoRegimen(negocio.getTipoRegimen())
                .responsabilidadFiscal(negocio.getResponsabilidadFiscal())
                .fechaRegistro(negocio.getFechaRegistro() != null ? negocio.getFechaRegistro().toString() : null)
                .ultimaActualizacion(negocio.getUltimaActualizacion() != null ? negocio.getUltimaActualizacion().toString() : null)
                .build();
    }

    // Convierte de NegocioDTO a Negocio
    public static Negocio convertDTOToNegocio(NegocioDTO negocioDTO) {
        if (negocioDTO == null) {
            return null;
        }
        return Negocio.builder()
                .nit(negocioDTO.getNit())
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
                // Convertir las fechas String a LocalDateTime si es necesario
                .fechaRegistro(negocioDTO.getFechaRegistro() != null ? LocalDateTime.parse(negocioDTO.getFechaRegistro()) : null)
                .ultimaActualizacion(negocioDTO.getUltimaActualizacion() != null ? LocalDateTime.parse(negocioDTO.getUltimaActualizacion()) : null)
                .build();
    }
}

