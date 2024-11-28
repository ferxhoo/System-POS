package system.pos.backend.service.Impl;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.PermisoRepository;
import system.pos.backend.Repository.RolRepository;
import system.pos.backend.dto.UsuarioRolPermiso.PermisoGuardarRolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperRol;
import system.pos.backend.model.Permiso;
import system.pos.backend.model.Rol;
import system.pos.backend.service.Interfaces.RolService;

@Service
public class RolServiceImpl implements RolService {

    private static final String ROL_NO_ENCONTRADO = "Rol no encontrado con el ID: ";
    private static final String ROL_DUPLICADO = "Ya existe un Rol con el nombre: ";
    private static final String PERMISO_NO_ENCONTRADO = "Permiso no encontrado para el ID: ";

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public void inicializarRolsDefault() {
        if (!rolRepository.existsByNombreRol("Administrador")) {
            List<Permiso> permisos = permisoRepository.findAll();
            Rol rolAdministrador = Rol.builder()
                    .nombreRol("Administrador")
                    .permisos(permisos)
                    .build();
            rolRepository.save(rolAdministrador);
        }
    }

    @Override
    @Transactional
    public RolDTO guardarRol(RolGuardarDTO rolGuardar) {
        validarRolDuplicado(rolGuardar.getNombreRol());
        Rol rolNuevo = Rol.builder()
                .nombreRol(rolGuardar.getNombreRol())
                .permisos(obtenerPermisosPorIds(rolGuardar.getPermisos()))
                .build();
        return MapperRol.ConvertRolDTO(rolRepository.save(rolNuevo));
    }

    @Override
    @Transactional(readOnly = true)
    public List<RolDTO> listarTodosLosRols() {
        List<Rol> roles = rolRepository.findAll();
        return roles.isEmpty() ? Collections.emptyList() : MapperRol.ConvertListRolDTO(roles);
    }

    @Override
    @Transactional(readOnly = true)
    public RolDTO obtenerRol(Long idRol) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException(ROL_NO_ENCONTRADO + idRol));
        return MapperRol.ConvertRolDTO(rol);
    }

    @Override
    @Transactional
    public RolDTO actualizarRol(Long idRol, RolGuardarDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException(ROL_NO_ENCONTRADO + idRol));

        if (!rolExistente.getNombreRol().equals(rolDTO.getNombreRol())) {
            validarRolDuplicado(rolDTO.getNombreRol());
        }

        rolExistente.setNombreRol(rolDTO.getNombreRol());
        rolExistente.setPermisos(obtenerPermisosPorIds(rolDTO.getPermisos()));

        return MapperRol.ConvertRolDTO(rolRepository.save(rolExistente));
    }

    @Override
    public void eliminarRol(Long idRol) {
        Rol rol = rolRepository.findById(idRol)
                .orElseThrow(() -> new ResourceNotFoundException(ROL_NO_ENCONTRADO + idRol));
        rolRepository.delete(rol);
    }

    private void validarRolDuplicado(String nombreRol) {
        if (rolRepository.existsByNombreRol(nombreRol)) {
            throw new ConflictException(ROL_DUPLICADO + nombreRol);
        }
    }

    private List<Permiso> obtenerPermisosPorIds(List<PermisoGuardarRolDTO> permisosDTO) {
        return permisosDTO.stream()
                .map(permiso -> permisoRepository.findById(permiso.getIdPermiso())
                        .orElseThrow(() -> new ResourceNotFoundException(PERMISO_NO_ENCONTRADO + permiso.getIdPermiso())))
                .collect(Collectors.toList());
    }
}
