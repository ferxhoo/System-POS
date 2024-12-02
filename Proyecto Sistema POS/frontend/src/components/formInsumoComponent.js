import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import categoriaServiceInstance from "../services/CategoriaService";
import unidadServiceInstance from "../services/UnidadService";
import insumoServiceInstance from "../services/InsumoService";

const FormInsumoComponent = () => {
    const [categorias, setCategorias] = useState([]);
    const [unidades, setUnidades] = useState([]);
    const [insumo, setInsumo] = useState({
        nombreInsumo: "",
        categoria: { idCategoria: "" },
        unidad: { idUnidad: "" },
        ultimaActualizacion: null,
    });

    const navigate = useNavigate();
    const { id } = useParams(); // Parámetro opcional para editar

    // Cargar categorías y unidades
    useEffect(() => {
        categoriaServiceInstance.getAllCategorias()
            .then((response) => {
                // Filtrar categorías por tipo "Insumo"
                const categoriasInsumo = response.data.filter(
                    (categoria) => categoria.tipoCategoria === "Insumo"
                );
                setCategorias(categoriasInsumo);
            })
            .catch((error) => console.error("Error al cargar categorías:", error));

        unidadServiceInstance.getAllUnidades()
            .then((response) => setUnidades(response.data))
            .catch((error) => console.error("Error al cargar unidades:", error));
    }, []);

    // Cargar datos del insumo si es edición
    useEffect(() => {
        if (id) {
            insumoServiceInstance.getInsumoById(id)
                .then((response) => setInsumo(response.data))
                .catch((error) => console.error("Error al cargar el insumo:", error));
        }
    }, [id]);

    // Manejar cambios en los inputs
    const handleChange = (e) => {
        const { name, value } = e.target;
        if (name === "categoria") {
            setInsumo({
                ...insumo,
                categoria: { idCategoria: value },
            });
        } else if (name === "unidad") {
            setInsumo({
                ...insumo,
                unidad: { idUnidad: value },
            });
        } else {
            setInsumo({ ...insumo, [name]: value });
        }
    };

    // Enviar datos
    const handleSubmit = (e) => {
        e.preventDefault();

        const dataToSend = {
            categoria: { idCategoria: insumo.categoria.idCategoria },
            unidad: { idUnidad: insumo.unidad.idUnidad },
            nombreInsumo: insumo.nombreInsumo,
            ultimaActualizacion: null,
        };

        if (id) {
            // Editar insumo
            insumoServiceInstance.editInsumo(id, dataToSend)
                .then(() => navigate("/insumos"))
                .catch((error) => console.error("Error al editar insumo:", error));
        } else {
            // Crear insumo
            insumoServiceInstance.saveInsumo(dataToSend)
                .then(() => navigate("/insumos"))
                .catch((error) => console.error("Error al registrar insumo:", error));
        }
    };

    const title = id ? "Editar Insumo" : "Registrar Insumo";

    return (
        <div className="mt-5 mb-4">
            <div className="container">
                <div className="row">
                    <div className="card col-md-6 offset-md-3">
                        <br />
                        <h2 className="text-center">{title}</h2>
                        <div className="card-body">
                            <form onSubmit={handleSubmit}>
                                {renderSelectField(
                                    "Categoría",
                                    "categoria",
                                    insumo.categoria.idCategoria,
                                    categorias,
                                    handleChange,
                                    "Seleccione una categoría"
                                )}
                                {renderInputField(
                                    "Nombre del Insumo",
                                    "nombreInsumo",
                                    insumo.nombreInsumo,
                                    handleChange
                                )}
                                {renderSelectField(
                                    "Unidad",
                                    "unidad",
                                    insumo.unidad.idUnidad,
                                    unidades,
                                    handleChange,
                                    "Seleccione una unidad"
                                )}
                                <div className="text-right">
                                    <button
                                        type="submit"
                                        className="btn btn-success"
                                        style={{ marginRight: "20px" }}
                                    >
                                        Guardar
                                    </button>
                                    <button
                                        type="button"
                                        className="btn btn-danger"
                                        onClick={() => navigate("/insumos")}
                                    >
                                        Cancelar
                                    </button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    );
};

const renderInputField = (label, name, value, onChange) => (
    <div className="form-group mb-3">
        <label htmlFor={name}>{label}</label>
        <input
            type="text"
            className="form-control"
            id={name}
            name={name}
            placeholder={label}
            value={value}
            onChange={onChange}
        />
    </div>
);

const renderSelectField = (label, name, value, options, onChange, placeholder) => (
    <div className="form-group mb-3">
        <label htmlFor={name}>{label}</label>
        <select
            className="form-control"
            id={name}
            name={name}
            value={value}
            onChange={onChange}
            required
        >
            <option value="">{placeholder}</option>
            {options.map((option) => (
                <option key={option.idCategoria || option.idUnidad} value={option.idCategoria || option.idUnidad}>
                    {option.nombreCategoria || option.nombreUnidad}
                </option>
            ))}
        </select>
    </div>
);

export default FormInsumoComponent;
