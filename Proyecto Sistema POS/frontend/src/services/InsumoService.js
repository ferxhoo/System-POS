import axios from "axios";

const INSUMO_BASE_API_REST_URL = 'http://localhost:8080/systempos/api/v1/insumos';

class InsumoService {
    getAllInsumos() {
        return axios.get(INSUMO_BASE_API_REST_URL);
    }
    saveInsumo(insumo) {
        return axios.post(INSUMO_BASE_API_REST_URL, insumo);
    }
    getInsumoById(id) {
        return axios.get(`${INSUMO_BASE_API_REST_URL}/${id}`);
    }
    editInsumo(id, insumo) {
        return axios.put(`${INSUMO_BASE_API_REST_URL}/editar/${id}`, insumo);
    }
    deleteInsumo(id) {
        return axios.delete(`${INSUMO_BASE_API_REST_URL}/${id}`);
    }
}

const insumoServiceInstance = new InsumoService();
export default insumoServiceInstance;