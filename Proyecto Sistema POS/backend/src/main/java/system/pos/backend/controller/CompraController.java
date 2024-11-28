package system.pos.backend.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.Facturacion.CompraDTO;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.CompraService;

@RestController
@RequestMapping("/compras")
public class CompraController {

    @Autowired
    private CompraService compraService;

    private static final String COMPRA_NO_ENCONTRADA = "Compra no encontrada";

    @PostMapping
    public ResponseEntity<?> registrarCompra(@RequestBody CompraDTO compraDTO) {
        try {
            CompraDTO compraRegistrada = compraService.registrarCompra(compraDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(compraRegistrada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "Error al registrar la compra", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al registrar la compra", e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> listarCompras() {
        try {
            List<CompraDTO> compras = compraService.listarCompras();
            return ResponseEntity.ok(compras);
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar las compras", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarCompraPorId(@PathVariable("id") Long id) {
        try {
            CompraDTO compra = compraService.buscarCompraPorId(id);
            return ResponseEntity.ok(compra);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, COMPRA_NO_ENCONTRADA, e.getMessage());
        }
    }

    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}

