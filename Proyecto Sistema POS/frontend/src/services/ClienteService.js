import axios from "axios";

const CLIENTE_BASE_API_REST_URL = 'http://localhost:8080/systempos/api/v1/clientes';

class ClienteService {
    getAllClientes() {
        return axios.get(CLIENTE_BASE_API_REST_URL);
    }
    saveCliente(cliente) {
        return axios.post(CLIENTE_BASE_API_REST_URL, cliente);
    }
    getClienteById(id) {
        return axios.get(`${CLIENTE_BASE_API_REST_URL}/${id}`);
    }
    editCliente(id, cliente) {
        return axios.put(`${CLIENTE_BASE_API_REST_URL}/editar/${id}`, cliente);
    }
    deleteCliente(id) {
        return axios.delete(`${CLIENTE_BASE_API_REST_URL}/${id}`);
    }
}

const clienteServiceInstance = new ClienteService();
export default clienteServiceInstance;
