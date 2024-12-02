import axios from "axios";

const UNIDAD_BASE_API_REST_URL = "http://localhost:8080/systempos/api/v1/unidades";

class UnidadService {
    getAllUnidades() {
        return axios.get(UNIDAD_BASE_API_REST_URL);
    }
    saveUnidad(unidad) {
        return axios.post(UNIDAD_BASE_API_REST_URL, unidad);
    }
    getUnidadById(id) {
        return axios.get(`${UNIDAD_BASE_API_REST_URL}/${id}`);
    }
    editUnidad(id, unidad) {
        return axios.put(`${UNIDAD_BASE_API_REST_URL}/editar/${id}`, unidad);
    }
    deleteUnidad(id) {
        return axios.delete(`${UNIDAD_BASE_API_REST_URL}/${id}`);
    }
}

const unidadServiceInstance = new UnidadService();
export default unidadServiceInstance;
