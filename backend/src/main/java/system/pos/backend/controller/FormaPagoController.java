package system.pos.backend.controller;

import java.util.Collections;
import java.util.List;
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

import system.pos.backend.dto.Contabilidad.FormaPagoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.FormaPagoService;

@RestController
@RequestMapping("/formasPago")
public class FormaPagoController {

    @Autowired
    private FormaPagoService formaPagoService;

    private static final String FORMA_PAGO_NO_ENCONTRADA = "Forma de pago no encontrada";
    private static final String FORMA_PAGO_EXISTENTE = "Forma de pago ya existe";
    private static final String FORMA_PAGO_ERROR_ACTUALIZACION = "Error al actualizar la forma de pago";

    @GetMapping
    public ResponseEntity<?> obtenerTodasFormasPago() {
        try {
            List<FormaPagoDTO> formasPago = formaPagoService.obtenerListaFormasPago();
            return ResponseEntity.ok(formasPago);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, "No se encontraron formas de pago", e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> buscarFormaPagoPorId(@PathVariable("id") Long id) {
        try {
            FormaPagoDTO formaPago = formaPagoService.buscarFormaPagoPorId(id);
            return ResponseEntity.ok(formaPago);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, FORMA_PAGO_NO_ENCONTRADA, e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity<?> guardarFormaPago(@RequestBody FormaPagoDTO formaPago) {
        try {
            FormaPagoDTO formaPagoGuardada = formaPagoService.guardarFormaPago(formaPago);
            return ResponseEntity.status(HttpStatus.CREATED).body(formaPagoGuardada);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, FORMA_PAGO_EXISTENTE, e.getMessage());
        }
    }

    @PutMapping("/editar/{id}")
    public ResponseEntity<?> actualizarFormaPago(@PathVariable("id") Long id, @RequestBody FormaPagoDTO formaPago) {
        try {
            FormaPagoDTO formaPagoActualizada = formaPagoService.actualizarFormaPago(id, formaPago);
            return ResponseEntity.ok(formaPagoActualizada);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, FORMA_PAGO_NO_ENCONTRADA, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, FORMA_PAGO_ERROR_ACTUALIZACION, e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarFormaPago(@PathVariable("id") Long id) {
        try {
            formaPagoService.eliminarFormaPago(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Forma de pago eliminada con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, FORMA_PAGO_NO_ENCONTRADA, e.getMessage());
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
