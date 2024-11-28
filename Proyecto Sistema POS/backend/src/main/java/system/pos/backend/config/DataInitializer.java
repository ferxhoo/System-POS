package system.pos.backend.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;
import system.pos.backend.service.Interfaces.CategoriaService;
import system.pos.backend.service.Interfaces.ClienteService;
import system.pos.backend.service.Interfaces.FormaPagoService;
import system.pos.backend.service.Interfaces.ImpuestoService;
import system.pos.backend.service.Interfaces.PermisoService;
import system.pos.backend.service.Interfaces.RolService;
import system.pos.backend.service.Interfaces.UnidadService;

@Component
public class DataInitializer {

    @Autowired
    private PermisoService permisoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private CategoriaService categoriaService;

    @Autowired
    private UnidadService unidadService;

    @Autowired
    private FormaPagoService formaPagoService;

    @Autowired
    private ImpuestoService impuestoService;

    @PostConstruct
    public void init() {
        permisoService.inicializarPermisosDefault();
        rolService.inicializarRolsDefault();
        clienteService.inicializarClienteGenerico();
        categoriaService.inicializarCategoriasDefault();
        unidadService.inicializarUnidadesDefeault();
        formaPagoService.inicializarFormasPagoDefeault();
        impuestoService.inicializarImpuestosDefeault();
    }

}

