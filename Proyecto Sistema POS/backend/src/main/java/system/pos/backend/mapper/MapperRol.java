package system.pos.backend.mapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.model.Rol;

public class MapperRol {

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");

    // Convertir Rol a RolDTO
    public static RolDTO ConvertRolDTO(Rol rol) {
        if (rol == null) {
            return null;
        }

        return RolDTO.builder()
                .idRol(rol.getIdRol())
                .nombreRol(rol.getNombreRol())
                .permisos(rol.getPermisos() != null 
                        ? rol.getPermisos().stream()
                              .map(MapperPermiso::ConvertPermisoDTO) 
                              .collect(Collectors.toList())
                        : null)
                .ultimaActualizacion(rol.getUltimaActualizacion() != null 
                        ? rol.getUltimaActualizacion().format(DATE_FORMATTER) 
                        : null)
                .build();
    }

    // Convertir RolDTO a Rol
    public static Rol ConvertRol(RolDTO rolDTO) {
        if (rolDTO == null) {
            return null;
        }

        return Rol.builder()
                .idRol(rolDTO.getIdRol())
                .nombreRol(rolDTO.getNombreRol())
                .permisos(rolDTO.getPermisos() != null 
                        ? rolDTO.getPermisos().stream()
                              .map(MapperPermiso::ConvertPermiso) 
                              .collect(Collectors.toList())
                        : null)
                .ultimaActualizacion(rolDTO.getUltimaActualizacion() != null 
                        ? LocalDateTime.parse(rolDTO.getUltimaActualizacion(), DATE_FORMATTER) 
                        : null)
                .build();
    }

    // Convertir una lista de Rol a una lista de RolDTO
    public static List<RolDTO> ConvertListRolDTO(List<Rol> roles) {
        if (roles == null) {
            return null;
        }

        return roles.stream()
                .map(MapperRol::ConvertRolDTO)
                .collect(Collectors.toList());
    }

    // Convertir una lista de RolDTO a una lista de Rol
    public static List<Rol> ConvertListRol(List<RolDTO> rolesDTO) {
        if (rolesDTO == null) {
            return null;
        }

        return rolesDTO.stream()
                .map(MapperRol::ConvertRol)
                .collect(Collectors.toList());
    }
}
