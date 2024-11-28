package system.pos.backend.mapper;

import java.util.List;
import java.util.stream.Collectors;

import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;
import system.pos.backend.model.Cliente;

public class MapperCliente {

    // Convierte un ClienteDTO a una entidad Cliente
    public static Cliente convertCliente(ClienteDTO clienteDTO) {
        if (clienteDTO == null) {
            return null;
        }
        return Cliente.builder()
                .idCliente(clienteDTO.getIdCliente())
                .tipoDocumento(clienteDTO.getTipoDocumento())
                .numeroDocumento(clienteDTO.getNumeroDocumento())
                .nombreCompleto(clienteDTO.getNombreCompleto())
                .primerApellido(clienteDTO.getPrimerApellido())
                .segundoApellido(clienteDTO.getSegundoApellido())
                .direccion(clienteDTO.getDireccion())
                .pais(clienteDTO.getPais())
                .departamento(clienteDTO.getDepartamento())
                .ciudad(clienteDTO.getCiudad())
                .telefono(clienteDTO.getTelefono())
                .email(clienteDTO.getEmail())
                .build();
    }

    // Convierte una entidad Cliente a un ClienteDTO
    public static ClienteDTO convertClienteDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        return ClienteDTO.builder()
                .idCliente(cliente.getIdCliente())
                .tipoDocumento(cliente.getTipoDocumento())
                .numeroDocumento(cliente.getNumeroDocumento())
                .nombreCompleto(cliente.getNombreCompleto())
                .primerApellido(cliente.getPrimerApellido())
                .segundoApellido(cliente.getSegundoApellido())
                .direccion(cliente.getDireccion())
                .pais(cliente.getPais())
                .departamento(cliente.getDepartamento())
                .ciudad(cliente.getCiudad())
                .telefono(cliente.getTelefono())
                .email(cliente.getEmail())
                .ultimaActualizacion(cliente.getUltimaActualizacion() != null 
                        ? cliente.getUltimaActualizacion().toString() 
                        : null)
                .build();
    }

    // Convierte una lista de ClienteDTO a una lista de entidades Cliente
    public static List<Cliente> convertListCliente(List<ClienteDTO> clienteDTOs) {
        if (clienteDTOs == null || clienteDTOs.isEmpty()) {
            return List.of();
        }
        return clienteDTOs.stream()
                .map(MapperCliente::convertCliente)
                .collect(Collectors.toList());
    }

    // Convierte una lista de entidades Cliente a una lista de ClienteDTO
    public static List<ClienteDTO> convertListClienteDTO(List<Cliente> clientes) {
        if (clientes == null || clientes.isEmpty()) {
            return List.of();
        }
        return clientes.stream()
                .map(MapperCliente::convertClienteDTO)
                .collect(Collectors.toList());
    }
}

