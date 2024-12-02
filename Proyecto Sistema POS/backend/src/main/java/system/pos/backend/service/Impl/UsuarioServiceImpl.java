package system.pos.backend.service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.RolRepository;
import system.pos.backend.Repository.UsuarioRepository;
import system.pos.backend.dto.UsuarioRolPermiso.UsuarioDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperUsuario;
import system.pos.backend.model.Rol;
import system.pos.backend.model.Usuario;
import system.pos.backend.service.Interfaces.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private static final String USUARIO_NO_ENCONTRADO = "Usuario no encontrado con el ID: ";
    private static final String USERNAME_DUPLICADO = "Ya existe un usuario con el nombre de usuario: ";
    private static final String EMAIL_DUPLICADO = "Ya existe un usuario con el correo electrónico: ";

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDTO> listarTodosLosUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        if (usuarios.isEmpty()) {
            return Collections.emptyList();
        }
        return usuarios.stream()
                .map(MapperUsuario::convertUsuarioToDTO)
                .collect(Collectors.toList());
    }



    @Override
    @Transactional
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO) {
        validarUsernameYEmailDuplicados(usuarioDTO.getUsername(), usuarioDTO.getEmail());

        Rol rol = rolRepository.findById(usuarioDTO.getRol().getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + usuarioDTO.getRol().getIdRol()));

        Usuario usuarioNuevo = Usuario.builder()
                .tipoDocumento(usuarioDTO.getTipoDocumento())
                .numeroDocumento(usuarioDTO.getNumeroDocumento())
                .nombreCompleto(usuarioDTO.getNombreCompleto())
                .primerApellido(usuarioDTO.getPrimerApellido())
                .segundoApellido(usuarioDTO.getSegundoApellido())
                .username(usuarioDTO.getUsername())
                .email(usuarioDTO.getEmail())
                .telefono(usuarioDTO.getTelefono())
                .password("hashedPassword")
                .rol(rol)
                .build();

        return MapperUsuario.convertUsuarioToDTO(usuarioRepository.save(usuarioNuevo));
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDTO obtenerUsuarioPorId(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO + idUsuario));
        return MapperUsuario.convertUsuarioToDTO(usuario);
    }

    @Override
    @Transactional
    public UsuarioDTO actualizarUsuario(Long idUsuario, UsuarioDTO usuarioDTO) {
        Usuario usuarioExistente = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO + idUsuario));

        // Validar duplicados si el username o email han cambiado
        if (!usuarioExistente.getUsername().equals(usuarioDTO.getUsername()) || 
            !usuarioExistente.getEmail().equals(usuarioDTO.getEmail())) {
            validarUsernameYEmailDuplicados(usuarioDTO.getUsername(), usuarioDTO.getEmail());
        }

        Rol rol = rolRepository.findById(usuarioDTO.getRol().getIdRol())
                .orElseThrow(() -> new ResourceNotFoundException("Rol no encontrado con el ID: " + usuarioDTO.getRol().getIdRol()));

        usuarioExistente.setTipoDocumento(usuarioDTO.getTipoDocumento());
        usuarioExistente.setNumeroDocumento(usuarioDTO.getNumeroDocumento());
        usuarioExistente.setNombreCompleto(usuarioDTO.getNombreCompleto());
        usuarioExistente.setPrimerApellido(usuarioDTO.getPrimerApellido());
        usuarioExistente.setSegundoApellido(usuarioDTO.getSegundoApellido());
        usuarioExistente.setUsername(usuarioDTO.getUsername());
        usuarioExistente.setEmail(usuarioDTO.getEmail());
        usuarioExistente.setTelefono(usuarioDTO.getTelefono());
        usuarioExistente.setRol(rol);

        return MapperUsuario.convertUsuarioToDTO(usuarioRepository.save(usuarioExistente));
    }

    @Override
    public void eliminarUsuario(Long idUsuario) {
        Usuario usuario = usuarioRepository.findById(idUsuario)
                .orElseThrow(() -> new ResourceNotFoundException(USUARIO_NO_ENCONTRADO + idUsuario));
        usuarioRepository.delete(usuario);
    }

    private void validarUsernameYEmailDuplicados(String username, String email) {
        if (usuarioRepository.existsByUsername(username)) {
            throw new ConflictException(USERNAME_DUPLICADO + username);
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException(EMAIL_DUPLICADO + email);
        }
    }
}

