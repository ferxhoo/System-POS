import React, { useState, useEffect } from "react";
import { useNavigate, useParams } from "react-router-dom";
import categoriaServiceInstance from "../services/CategoriaService";
import impuestoServiceInstance from "../services/ImpuestoService";
import productoServiceInstance from "../services/ProductoService";

const FormProductoComponent = () => {
    const [categorias, setCategorias] = useState([]);
    const [impuestos, setImpuestos] = useState([]);
    const [producto, setProducto] = useState({
        nombreProducto: "",
        categoria: { idCategoria: "" },
        inventariable: true,
        precioUnitario: "",
        impuesto: { idImpuesto: "1" }, 
    });

    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        categoriaServiceInstance.getAllCategorias()
            .then((response) => {
                const categoriasProducto = response.data.filter(
                    (categoria) => categoria.tipoCategoria === "Producto"
                );
                setCategorias(categoriasProducto);
            })
            .catch((error) => console.error("Error al cargar categorías:", error));

        impuestoServiceInstance.getAllImpuestos()
            .then((response) => setImpuestos(response.data))
            .catch((error) => console.error("Error al cargar impuestos:", error));
    }, []);

    useEffect(() => {
        if (id) {
            productoServiceInstance.getProductoById(id)
                .then((response) => {
                    const data = response.data;
                    setProducto({
                        ...data,
                        categoria: { idCategoria: data.categoria?.idCategoria || "" },
                        impuesto: { idImpuesto: data.impuesto?.idImpuesto || "1" },
                    });
                })
                .catch((error) => console.error("Error al cargar el producto:", error));
        }
    }, [id]);

    const handleChange = (e) => {
        const { name, value } = e.target;

        if (name === "precioUnitario") {
            if (value < 0) {
                alert("El precio no puede ser negativo.");
                return;
            }
        }

        if (name === "categoria") {
            setProducto({
                ...producto,
                categoria: { idCategoria: value },
            });
        } else if (name === "impuesto") {
            setProducto({
                ...producto,
                impuesto: { idImpuesto: value },
            });
        } else if (name === "inventariable") {
            setProducto({
                ...producto,
                inventariable: value === "true",
            });
        } else {
            setProducto({ ...producto, [name]: value });
        }
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        const dataToSend = {
            ...producto,
            categoria: { idCategoria: producto.categoria.idCategoria },
            impuesto: { idImpuesto: producto.impuesto.idImpuesto },
        };

        if (id) {
            productoServiceInstance.editProducto(id, dataToSend)
                .then(() => navigate("/productos"))
                .catch((error) => console.error("Error al editar producto:", error));
        } else {
            productoServiceInstance.saveProducto(dataToSend)
                .then(() => navigate("/productos"))
                .catch((error) => console.error("Error al registrar producto:", error));
        }
    };

    return (
        <div className="mt-5 mb-4">
            <div className="container">
                <div className="row">
                    <div className="card col-md-6 offset-md-3">
                        <br />
                        <h2 className="text-center">{id ? "Editar Producto" : "Registrar Producto"}</h2>
                        <div className="card-body">
                            <form onSubmit={handleSubmit}>
                                {renderSelectField(
                                    "Categoría",
                                    "categoria",
                                    producto.categoria.idCategoria,
                                    categorias,
                                    handleChange,
                                    "Seleccione una categoría"
                                )}
                                {renderInputField(
                                    "Nombre del Producto",
                                    "nombreProducto",
                                    producto.nombreProducto,
                                    handleChange
                                )}
                                {renderYesNoDropdown(handleChange, producto.inventariable)}
                                {renderInputField(
                                    "Precio Unitario",
                                    "precioUnitario",
                                    producto.precioUnitario,
                                    handleChange,
                                    "number"
                                )}
                                {renderSelectField(
                                    "Impuesto",
                                    "impuesto",
                                    producto.impuesto.idImpuesto,
                                    impuestos,
                                    handleChange,
                                    "Seleccione un impuesto"
                                )}
                                <div className="text-right">
                                    <button type="submit" className="btn btn-success mr-2">
                                        Guardar
                                    </button>
                                    <button
                                        type="button"
                                        className="btn btn-danger"
                                        onClick={() => navigate("/productos")}
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

const renderInputField = (label, name, value, onChange, type = "text") => (
    <div className="form-group mb-3">
        <label htmlFor={name}>{label}</label>
        <input
            type={type}
            className="form-control"
            id={name}
            name={name}
            value={value}
            onChange={onChange}
            required
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
                <option
                    key={option.idCategoria || option.idImpuesto}
                    value={option.idCategoria || option.idImpuesto}
                >
                    {option.nombreCategoria || `${option.nombreImpuesto} - ${option.concepto}`}
                </option>
            ))}
        </select>
    </div>
);

const renderYesNoDropdown = (onChange, value) => (
    <div className="form-group mb-3">
        <label htmlFor="inventariable">Inventariable</label>
        <select
            className="form-control"
            id="inventariable"
            name="inventariable"
            value={value.toString()}
            onChange={onChange}
            required
        >
            <option value="true">Sí</option>
            <option value="false">No</option>
        </select>
    </div>
);

export default FormProductoComponent;
