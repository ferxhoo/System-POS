import axios from "axios";

const PRODUCTO_BASE_API_REST_URL = "http://localhost:8080/systempos/api/v1/productos";

class ProductoService {
    getAllProductos() {
        return axios.get(PRODUCTO_BASE_API_REST_URL);
    }

    saveProducto(producto) {
        return axios.post(PRODUCTO_BASE_API_REST_URL, producto);
    }

    getProductoById(id) {
        return axios.get(`${PRODUCTO_BASE_API_REST_URL}/${id}`);
    }

    editProducto(id, producto) {
        return axios.put(`${PRODUCTO_BASE_API_REST_URL}/editar/${id}`, producto);
    }

    deleteProducto(id) {
        return axios.delete(`${PRODUCTO_BASE_API_REST_URL}/${id}`);
    }
}

const productoServiceInstance = new ProductoService();
export default productoServiceInstance;
