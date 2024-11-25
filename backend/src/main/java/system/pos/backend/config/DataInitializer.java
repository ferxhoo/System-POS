package system.pos.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import system.pos.backend.service.Interfaces.PermisoService;
import system.pos.backend.service.Interfaces.RolService;

@Component
public class DataInitializer {

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private RolService rolService;

    @PostConstruct
    public void init() {
        permisoService.inicializarPermisosDefault();
        rolService.inicializarRolsDefault();
    }

}

