import axios from "axios";

const INVENTARIO_BASE_API_REST_URL = "http://localhost:8080/systempos/api/v1/inventario";

class InventarioService {
    getInventario() {
        return axios.get(INVENTARIO_BASE_API_REST_URL);
    }
}

const inventarioServiceInstance = new InventarioService();
export default inventarioServiceInstance;
