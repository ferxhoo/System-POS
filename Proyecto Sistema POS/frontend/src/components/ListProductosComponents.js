import React, { useEffect, useState } from 'react';
import productoService from '../services/ProductoService';
import { Link } from 'react-router-dom';

const ListProductosComponents = () => {
    const [productos, setProductos] = useState([]);

    useEffect(() => {
        fetchProductos();
    }, []);

    const fetchProductos = () => {
        productoService.getAllProductos()
            .then(response => {
                setProductos(response.data);
            })
            .catch(error => {
                console.error('Error al cargar los productos:', error);
            });
    };

    const handleDelete = (idProducto) => {
        if (window.confirm('¿Está seguro de que desea eliminar este producto?')) {
            productoService.deleteProducto(idProducto)
                .then(() => {
                    fetchProductos(); // Actualiza la lista después de eliminar
                })
                .catch(error => {
                    console.error('Error al eliminar el producto:', error);
                });
        }
    };

    return (
        <div className="mx-4">
            <div className="mt-4">
                <h1 className="text-center">Lista de Productos</h1>
                <Link to="/producto/registro" className="btn btn-primary mb-3">
                    Registrar Producto
                </Link>
                <table className="table table-bordered table-striped">
                    <thead className="bg-primary text-white thead-dark">
                        <tr>
                            <th className="text-center">Categoría</th>
                            <th className="text-center">Nombre</th>
                            <th className="text-center">Inventariable</th>
                            <th className="text-center">Precio</th>
                            <th className="text-center">Impuesto</th>
                            <th className="text-center">Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {productos.length > 0 ? (
                            productos.map(producto => (
                                <tr key={producto.idProducto}>
                                    <td>{producto.categoria?.nombreCategoria || 'Sin categoría'}</td>
                                    <td>{producto.nombreProducto}</td>
                                    <td>{producto.inventariable ? 'Sí' : 'No'}</td>
                                    <td>{producto.precioUnitario}</td>
                                    <td>{`${producto.impuesto?.nombreImpuesto || 'No tiene impuesto'} - ${producto.impuesto?.concepto || ''}`}</td>
                                    <td>
                                        <div className="d-flex justify-content-center">
                                            <Link to={`/producto/editar/${producto.idProducto}`} className="btn btn-primary mr-3">
                                                Editar
                                            </Link>
                                            <button className="btn btn-danger ml-3" onClick={() => handleDelete(producto.idProducto)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="6" className="text-center">
                                    No hay productos disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListProductosComponents;
