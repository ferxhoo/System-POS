package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.UsuarioRolPermiso.PermisoDTO;
import system.pos.backend.model.Permiso;

public class MapperPermiso {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Convertir Permiso a PermisoDTO
    public static PermisoDTO ConvertPermisoDTO(Permiso permiso) {
        if (permiso == null) {
            return null;
        }

        return PermisoDTO.builder()
                .idPermiso(permiso.getIdPermiso())
                .modulo(permiso.getModulo())
                .nombrePermiso(permiso.getNombrePermiso())
                .ruta(permiso.getRuta())
                .fechaRegistro(permiso.getFechaRegistro() != null ? permiso.getFechaRegistro().format(DATE_FORMATTER) : null)
                .build();
    }

    // Convertir PermisoDTO a Permiso
    public static Permiso ConvertPermiso(PermisoDTO permisoDTO) {
        if (permisoDTO == null) {
            return null;
        }

        return Permiso.builder()
                .idPermiso(permisoDTO.getIdPermiso())
                .modulo(permisoDTO.getModulo())
                .nombrePermiso(permisoDTO.getNombrePermiso())
                .ruta(permisoDTO.getRuta())
                .fechaRegistro(permisoDTO.getFechaRegistro() != null ? LocalDateTime.parse(permisoDTO.getFechaRegistro(), DATE_FORMATTER) : null)
                .build();
    }

    // Convertir una lista de Permiso a una lista de PermisoDTO
    public static List<PermisoDTO> ConvertListPermisoDTO(List<Permiso> permisos) {
        if (permisos == null) {
            return null;
        }

        return permisos.stream()
                .map(MapperPermiso::ConvertPermisoDTO)
                .collect(Collectors.toList());
    }

    // Convertir una lista de PermisoDTO a una lista de Permiso
    public static List<Permiso> ConvertListPermiso(List<PermisoDTO> permisoDTOs) {
        if (permisoDTOs == null) {
            return null;
        }

        return permisoDTOs.stream()
                .map(MapperPermiso::ConvertPermiso)
                .collect(Collectors.toList());
    }
}

