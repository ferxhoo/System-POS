import axios from "axios";

const IMPUESTO_BASE_API_REST_URL = "http://localhost:8080/systempos/api/v1/impuestos";

class ImpuestoService {
    getAllImpuestos() {
        return axios.get(IMPUESTO_BASE_API_REST_URL);
    }
    saveImpuesto(impuesto) {
        return axios.post(IMPUESTO_BASE_API_REST_URL, impuesto);
    }
    getImpuestoById(id) {
        return axios.get(`${IMPUESTO_BASE_API_REST_URL}/${id}`);
    }
    editImpuesto(id, impuesto) {
        return axios.put(`${IMPUESTO_BASE_API_REST_URL}/editar/${id}`, impuesto);
    }
    deleteImpuesto(id) {
        return axios.delete(`${IMPUESTO_BASE_API_REST_URL}/${id}`);
    }
}

const impuestoServiceInstance = new ImpuestoService();
export default impuestoServiceInstance;
