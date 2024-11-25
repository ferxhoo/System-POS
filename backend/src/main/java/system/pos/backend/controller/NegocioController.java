package system.pos.backend.controller;

import java.util.Collections;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.Negocio.NegocioDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.NegocioService;

@RestController
@RequestMapping("/negocio")
public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    private static final String NEGOCIO_NO_ENCONTRADO = "Negocio no encontrado";

    @GetMapping("/{nit}")
    public ResponseEntity<?> buscarNegocioPorNit(@PathVariable("nit") String nit) {
        try {
            NegocioDTO negocio = negocioService.buscarPorNit(nit);
            return ResponseEntity.ok(negocio);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, NEGOCIO_NO_ENCONTRADO, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarNegocio(@RequestBody NegocioDTO negocioDTO) {
        try {
            NegocioDTO negocioGuardado = negocioService.guardarNegocio(negocioDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(negocioGuardado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al guardar el negocio", e.getMessage());
        }
    }

    @PutMapping("/editar/{nit}")
    public ResponseEntity<?> actualizarNegocio(@PathVariable("nit") String nit, @RequestBody NegocioDTO negocioDTO) {
        try {
            NegocioDTO negocioActualizado = negocioService.actualizarNegocio(nit, negocioDTO);
            return ResponseEntity.ok(negocioActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, NEGOCIO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar el negocio", e.getMessage());
        }
    }

    @DeleteMapping("/{nit}")
    public ResponseEntity<?> eliminarNegocio(@PathVariable("nit") String nit) {
        try {
            negocioService.eliminarNegocio(nit);
            return ResponseEntity.ok(Collections.singletonMap("message", "Negocio eliminado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, NEGOCIO_NO_ENCONTRADO, e.getMessage());
        }
    }

    // Método genérico para generar respuestas de error
    private ResponseEntity<Map<String, String>> generarRespuestaError(HttpStatus status, String error, String detalle) {
        return ResponseEntity.status(status).body(Map.of(
                "error", error,
                "detalle", detalle
        ));
    }
}
