package system.pos.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import system.pos.backend.service.Impl.PermisoServiceImpl;
import system.pos.backend.service.Impl.RolServiceImpl;

@Component
public class DataInitializer {

    @Autowired
    private PermisoServiceImpl permisoService;

    @Autowired
    private RolServiceImpl rolService;

    @PostConstruct
    public void init() {
        permisoService.inicializarPermisosDefault();
        rolService.inicializarRolsDefault();
    }

}

