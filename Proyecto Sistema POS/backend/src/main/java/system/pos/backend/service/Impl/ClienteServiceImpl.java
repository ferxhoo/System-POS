package system.pos.backend.service.Impl;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import system.pos.backend.Repository.ClienteRepository;
import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;
import system.pos.backend.exception.ConflictException;
import system.pos.backend.exception.ResourceNotFoundException;
import system.pos.backend.mapper.MapperCliente;
import system.pos.backend.model.Cliente;
import system.pos.backend.service.Interfaces.ClienteService;

@Service
public class ClienteServiceImpl implements ClienteService {

    private static final String CLIENTE_NO_ENCONTRADO = "Cliente no encontrado con el ID: ";
    private static final String CLIENTE_EXISTENTE = "Ya existe un cliente con este número de documento: ";
    private static final String NUMERO_DOCUMENTO_GENERICO = "22222222222";

    @Autowired
    private ClienteRepository clienteRepository;

    @Override
    @Transactional
    public void inicializarClienteGenerico() {
        clienteRepository.findByNumeroDocumentoIgnoreCase(NUMERO_DOCUMENTO_GENERICO)
                .orElseGet(() -> clienteRepository.save(crearClienteGenerico()));
    }

    private Cliente crearClienteGenerico() {
        return Cliente.builder()
                .tipoDocumento("Cédula")
                .numeroDocumento(NUMERO_DOCUMENTO_GENERICO)
                .nombreCompleto("Consumidor Final")
                .primerApellido("Genérico")
                .segundoApellido("Desconocido")
                .direccion("No disponible")
                .pais("No disponible")
                .departamento("No disponible")
                .ciudad("No disponible")
                .telefono("No disponible")
                .email("No disponible")
                .build();
    }

    @Override
    @Transactional(readOnly = true)
    public ClienteDTO buscarClientePorId(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO + id));
        return MapperCliente.convertClienteDTO(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ClienteDTO> obtenerTodosClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        return clientes.isEmpty() ? Collections.emptyList() : MapperCliente.convertListClienteDTO(clientes);
    }

    @Override
    @Transactional
    public ClienteDTO guardarCliente(ClienteDTO clienteGuardar) {
        validarClienteExistente(clienteGuardar.getNumeroDocumento());
        Cliente nuevoCliente = MapperCliente.convertCliente(clienteGuardar);
        return MapperCliente.convertClienteDTO(clienteRepository.save(nuevoCliente));
    }

    @Override
    @Transactional
    public ClienteDTO actualizarCliente(Long id, ClienteDTO clienteActualizado) {
        Cliente clienteActual = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO + id));

        if (!clienteActual.getNumeroDocumento().equals(clienteActualizado.getNumeroDocumento())) {
            validarClienteExistente(clienteActualizado.getNumeroDocumento());
        }

        clienteActual = actualizarDatosCliente(clienteActual, clienteActualizado);
        return MapperCliente.convertClienteDTO(clienteRepository.save(clienteActual));
    }

    @Override
    @Transactional
    public void eliminarCliente(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(CLIENTE_NO_ENCONTRADO + id));
        clienteRepository.delete(cliente);
    }

    // Métodos auxiliares
    private void validarClienteExistente(String numeroDocumento) {
        if (clienteRepository.existsByNumeroDocumentoIgnoreCase(numeroDocumento)) {
            throw new ConflictException(CLIENTE_EXISTENTE + numeroDocumento);
        }
    }

    private Cliente actualizarDatosCliente(Cliente clienteActual, ClienteDTO clienteActualizado) {
        return clienteActual.toBuilder()
                .tipoDocumento(clienteActualizado.getTipoDocumento())
                .numeroDocumento(clienteActualizado.getNumeroDocumento())
                .nombreCompleto(clienteActualizado.getNombreCompleto())
                .primerApellido(clienteActualizado.getPrimerApellido())
                .segundoApellido(clienteActualizado.getSegundoApellido())
                .direccion(clienteActualizado.getDireccion())
                .pais(clienteActualizado.getPais())
                .departamento(clienteActualizado.getDepartamento())
                .ciudad(clienteActualizado.getCiudad())
                .telefono(clienteActualizado.getTelefono())
                .email(clienteActualizado.getEmail())
                .build();
    }
}
