package system.pos.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioLoginDTO;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioRegistroDTO;
import system.pos.backend.model.Usuario;

public class MapperUsuario {

    // Convertir Usuario a UsuarioDTO
    public static UsuarioDTO convertUsuarioToDTO(Usuario usuario) {
        if (usuario == null) {
            return null;
        }
        return UsuarioDTO.builder()
                .idUsuario(usuario.getIdUsuario())
                .tipoDocumento(usuario.getTipoDocumento())
                .numeroDocumento(usuario.getNumeroDocumento())
                .nombreCompleto(usuario.getNombreCompleto())
                .primerApellido(usuario.getPrimerApellido())
                .segundoApellido(usuario.getSegundoApellido())
                .username(usuario.getUsername())
                .email(usuario.getEmail())
                .telefono(usuario.getTelefono())
                .rol(MapperRol.ConvertRolDTO(usuario.getRol())) // Usando MapperRol para RolDTO
                .fechaCreacion(usuario.getFechaCreacion() != null ? usuario.getFechaCreacion().toString() : null)
                .ultimaActualizacion(usuario.getUltimaActualizacion() != null ? usuario.getUltimaActualizacion().toString() : null)
                .build();
    }

    // Convertir UsuarioDTO a Usuario
    public static Usuario convertDTOToUsuario(UsuarioDTO usuarioDTO) {
        if (usuarioDTO == null) {
            return null;
        }
        return Usuario.builder()
                .idUsuario(usuarioDTO.getIdUsuario())
                .tipoDocumento(usuarioDTO.getTipoDocumento())
                .numeroDocumento(usuarioDTO.getNumeroDocumento())
                .nombreCompleto(usuarioDTO.getNombreCompleto())
                .primerApellido(usuarioDTO.getPrimerApellido())
                .segundoApellido(usuarioDTO.getSegundoApellido())
                .username(usuarioDTO.getUsername())
                .email(usuarioDTO.getEmail())
                .telefono(usuarioDTO.getTelefono())
                .rol(MapperRol.ConvertRol(usuarioDTO.getRol())) // Usando MapperRol para Rol
                .build();
    }

    // Convertir UsuarioRegistroDTO a Usuario
    public static Usuario convertUsuarioRegistroDTOToUsuario(UsuarioRegistroDTO registroDTO) {
        if (registroDTO == null) {
            return null;
        }
        return Usuario.builder()
                .tipoDocumento(registroDTO.getTipoDocumento())
                .numeroDocumento(registroDTO.getNumeroDocumento())
                .nombreCompleto(registroDTO.getNombreCompleto())
                .primerApellido(registroDTO.getPrimerApellido())
                .segundoApellido(registroDTO.getSegundoApellido())
                .username(registroDTO.getUsername())
                .password(registroDTO.getPassword())
                .email(registroDTO.getEmail())
                .telefono(registroDTO.getTelefono())
                .build();
    }

    // Convertir UsuarioLoginDTO a Usuario
    public static Usuario convertUsuarioLoginDTOToUsuario(UsuarioLoginDTO loginDTO) {
        if (loginDTO == null) {
            return null;
        }
        return Usuario.builder()
                .username(loginDTO.getUsername())
                .password(loginDTO.getPassword())
                .build();
    }

    // Convertir lista de Usuario a lista de UsuarioDTO
    public static List<UsuarioDTO> convertListUsuarioToListDTO(List<Usuario> usuarios) {
        return usuarios.stream()
                .map(MapperUsuario::convertUsuarioToDTO)
                .collect(Collectors.toList());
    }

    // Convertir lista de UsuarioDTO a lista de Usuario
    public static List<Usuario> convertListDTOToListUsuario(List<UsuarioDTO> usuarioDTOs) {
        return usuarioDTOs.stream()
                .map(MapperUsuario::convertDTOToUsuario)
                .collect(Collectors.toList());
    }
}

