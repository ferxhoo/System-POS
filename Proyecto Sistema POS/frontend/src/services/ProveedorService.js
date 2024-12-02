import axios from "axios";

const PROVEEDOR_BASE_API_REST_URL = 'http://localhost:8080/systempos/api/v1/proveedores';

class ProveedorService {
    getAllProveedores() {
        return axios.get(PROVEEDOR_BASE_API_REST_URL);
    }
    saveProveedor(proveedor) {
        return axios.post(PROVEEDOR_BASE_API_REST_URL, proveedor);
    }
    getProveedorById(id) {
        return axios.get(`${PROVEEDOR_BASE_API_REST_URL}/${id}`);
    }
    editProveedor(id, proveedor) {
        return axios.put(`${PROVEEDOR_BASE_API_REST_URL}/editar/${id}`, proveedor);
    }
    deleteProveedor(id) {
        return axios.delete(`${PROVEEDOR_BASE_API_REST_URL}/${id}`);
    }
}

const proveedorServiceInstance = new ProveedorService();
export default proveedorServiceInstance;