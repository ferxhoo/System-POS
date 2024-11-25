package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;

public interface UsuarioService {
    List<UsuarioDTO> listarTodosLosUsuarios();
    UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO);
    UsuarioDTO obtenerUsuarioPorId(Long idUsuario);
    UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioDTO usuarioDTO);
    void eliminarUsuario(Long idUsuario);
}
