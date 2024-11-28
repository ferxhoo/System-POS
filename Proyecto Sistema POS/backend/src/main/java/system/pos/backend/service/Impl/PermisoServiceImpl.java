package system.pos.backend.service.Impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import system.pos.backend.Repository.PermisoRepository;
import system.pos.backend.dto.UsuarioRolPermiso.PermisoDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperPermiso;
import system.pos.backend.model.Permiso;
import system.pos.backend.service.Interfaces.PermisoService;


@Service
public class PermisoServiceImpl implements PermisoService {

    private static final String PERMISO_NO_ENCONTRADO = "No se encontraron permisos registrados";

    @Autowired
    private PermisoRepository permisoRepository;

    @Override
    public void inicializarPermisosDefault() {
        List<Permiso> permisosPorDefecto = crearListaDePermisosDefault();
        permisosPorDefecto.forEach(this::guardarPermisoSiNoExiste);
    }

    private List<Permiso> crearListaDePermisosDefault() {
        return List.of(
            Permiso.builder()
                .modulo("Categorias")
                .nombrePermiso("Ver Categorias")
                .ruta("/systempos/api/v1/categorias")
                .build(),
            Permiso.builder()
                .modulo("Cliente")
                .nombrePermiso("Ver Clientes")
                .ruta("/systempos/api/v1/clientes")
                .build(),
            Permiso.builder()
                .modulo("Formas de Pago")
                .nombrePermiso("Ver Forma de Pago")
                .ruta("/systempos/api/v1/formasPago")
                .build()
        );
    }

    private void guardarPermisoSiNoExiste(Permiso permiso) {
        if (!permisoRepository.existsByModuloAndNombrePermiso(permiso.getModulo(), permiso.getNombrePermiso())) {
            permisoRepository.save(permiso);
        }
    }

    @Override
    public List<PermisoDTO> listarTodosLosPermisos() {
        List<Permiso> permisos = permisoRepository.findAll();
        if (permisos.isEmpty()) {
            throw new ResourceNotFoundException(PERMISO_NO_ENCONTRADO);
        }
        return MapperPermiso.ConvertListPermisoDTO(permisos);
    }
}

