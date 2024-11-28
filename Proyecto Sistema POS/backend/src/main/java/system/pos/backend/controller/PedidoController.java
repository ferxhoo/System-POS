package system.pos.backend.controller;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import system.pos.backend.dto.Pedido.PedidoDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.service.Interfaces.PedidoService;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    private static final String PEDIDO_NO_ENCONTRADO = "Pedido no encontrado";

    // Crear un pedido
    @PostMapping
    public ResponseEntity<?> crearPedido(@RequestBody PedidoDTO pedidoDTO) {
        try {
            PedidoDTO pedidoCreado = pedidoService.crearPedido(pedidoDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoCreado);
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al crear el pedido", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al crear el pedido", e.getMessage());
        }
    }

    // Listar todos los pedidos
    @GetMapping
    public ResponseEntity<?> listarPedidos() {
        try {
            List<PedidoDTO> pedidos = pedidoService.listarPedidos();
            return ResponseEntity.ok(pedidos);
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error al listar los pedidos", e.getMessage());
        }
    }

    // Buscar un pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<?> buscarPedidoPorId(@PathVariable Long id) {
        try {
            PedidoDTO pedido = pedidoService.buscarPedidoPorId(id);
            return ResponseEntity.ok(pedido);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PEDIDO_NO_ENCONTRADO, e.getMessage());
        }
    }

    // Actualizar estado del pedido
    @PutMapping("/{id}/estado")
    public ResponseEntity<?> actualizarEstadoPedido(@PathVariable Long id, @RequestParam String estado) {
        try {
            PedidoDTO pedidoActualizado = pedidoService.actualizarEstadoPedido(id, estado);
            return ResponseEntity.ok(pedidoActualizado);
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PEDIDO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al actualizar el estado del pedido", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al actualizar el estado del pedido", e.getMessage());
        }
    }

    // Cancelar un pedido
    @PutMapping("/{id}/cancelar")
    public ResponseEntity<?> cancelarPedido(@PathVariable Long id) {
        try {
            pedidoService.cancelarPedido(id);
            return ResponseEntity.ok(Collections.singletonMap("message", "Pedido cancelado con éxito"));
        } catch (ResourceNotFoundException e) {
            return generarRespuestaError(HttpStatus.NOT_FOUND, PEDIDO_NO_ENCONTRADO, e.getMessage());
        } catch (ConflictException e) {
            return generarRespuestaError(HttpStatus.CONFLICT, "Error al cancelar el pedido", e.getMessage());
        } catch (Exception e) {
            return generarRespuestaError(HttpStatus.INTERNAL_SERVER_ERROR, "Error inesperado al cancelar el pedido", e.getMessage());
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

