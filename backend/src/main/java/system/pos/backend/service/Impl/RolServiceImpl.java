package system.pos.backend.service.Impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.PermisoRepository;
import system.pos.backend.Repository.RolRepository;
import system.pos.backend.dto.UsuarioRolPermiso.RolDTO;
import system.pos.backend.dto.UsuarioRolPermiso.RolGuardarDTO;
import system.pos.backend.mapper.MapperRol;
import system.pos.backend.model.Permiso;
import system.pos.backend.model.Rol;
import system.pos.backend.service.Interfaces.RolService;

@Service
public class RolServiceImpl implements RolService {

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public void inicializarRolsDefault() {
        if(!rolRepository.existsByNombreRol("Administrador")) {
            String rolAdmin = "Administrador";
            List<Permiso> permisos = permisoRepository.findAll();
            Rol rolAdministrador = Rol.builder()
                .nombreRol(rolAdmin)
                .permisos(permisos)
                .build();
            rolRepository.save(rolAdministrador);
        }
    }

    @Override
    @Transactional
    public RolDTO guardarRol(RolGuardarDTO rolGuardar) {
        Rol rolNuevo = new Rol();
        rolNuevo.setNombreRol(rolGuardar.getNombreRol());
        List<Permiso> permisos = rolGuardar.getPermisos().stream()
            .map(permisoGuardarRolDTO -> permisoRepository.findById(permisoGuardarRolDTO.getIdPermiso())
                    .orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado para el ID: " + permisoGuardarRolDTO.getIdPermiso())))
            .collect(Collectors.toList());
        rolNuevo.setPermisos(permisos);
        return MapperRol.ConvertRolDTO(rolRepository.save(rolNuevo));
    }

    @Override
    @Transactional
    public List<RolDTO> listarTodosLosRols() {
        List<Rol> roles = rolRepository.findAll();
        if (roles.isEmpty()) {
            return null;
        }
        return MapperRol.ConvertListRolDTO(roles);
    }

    @Override
    public RolDTO obtenerRol(Long idRol) {
        Rol rol = rolRepository.findById(idRol)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con el ID: " + idRol));
        return MapperRol.ConvertRolDTO(rol);
    }

    @Override
    public RolDTO actualizarRol(Long idRol, RolGuardarDTO rolDTO) {
        Rol rolExistente = rolRepository.findById(idRol)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con el ID: " + idRol));
        if(rolRepository.existsByNombreRol(rolDTO.getNombreRol())) {
            throw new IllegalArgumentException("Ya existe un Rol con el nombre: " + rolDTO.getNombreRol());
        }
        rolExistente.setNombreRol(rolDTO.getNombreRol());
        List<Permiso> permisos = rolDTO.getPermisos().stream()
            .map(permisoGuardarRolDTO -> permisoRepository.findById(permisoGuardarRolDTO.getIdPermiso())
                    .orElseThrow(() -> new IllegalArgumentException("Permiso no encontrado para el ID: " + permisoGuardarRolDTO.getIdPermiso())))
            .collect(Collectors.toList());
        rolExistente.setPermisos(permisos);
        return MapperRol.ConvertRolDTO(rolRepository.save(rolExistente));
    }

    @Override
    public void eliminarRol(Long idRol) {
        Rol rol = rolRepository.findById(idRol)
            .orElseThrow(() -> new IllegalArgumentException("Rol no encontrado con el ID: " + idRol));
        rolRepository.delete(rol);
    }

}
