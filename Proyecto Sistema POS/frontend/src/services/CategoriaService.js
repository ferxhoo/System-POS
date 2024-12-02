import axios from "axios";

const CATEGORIA_BASE_API_REST_URL = "http://localhost:8080/systempos/api/v1/categorias";

class CategoriaService {
    getAllCategorias() {
        return axios.get(CATEGORIA_BASE_API_REST_URL);
    }
    saveCategoria(categoria) {
        return axios.post(CATEGORIA_BASE_API_REST_URL, categoria);
    }
    getCategoriaById(id) {
        return axios.get(`${CATEGORIA_BASE_API_REST_URL}/${id}`);
    }
    editCategoria(id, categoria) {
        return axios.put(`${CATEGORIA_BASE_API_REST_URL}/editar/${id}`, categoria);
    }
    deleteCategoria(id) {
        return axios.delete(`${CATEGORIA_BASE_API_REST_URL}/${id}`);
    }
}

const categoriaServiceInstance = new CategoriaService();
export default categoriaServiceInstance;
