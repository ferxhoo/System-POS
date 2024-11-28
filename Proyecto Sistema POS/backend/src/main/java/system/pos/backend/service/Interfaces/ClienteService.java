package system.pos.backend.service.Interfaces;

import java.util.List;

import system.pos.backend.dto.StakeHolderExternos.ClienteDTO;

public interface ClienteService {
    void inicializarClienteGenerico();
    ClienteDTO buscarClientePorId(Long id);
    List<ClienteDTO> obtenerTodosClientes();
    ClienteDTO guardarCliente(ClienteDTO cliente);
    ClienteDTO actualizarCliente(Long id, ClienteDTO cliente);
    void eliminarCliente(Long id);
}
