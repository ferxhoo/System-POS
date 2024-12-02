import React, { useEffect, useState } from 'react';
import inventarioService from '../services/InventarioService';

const ListInventarioComponent = () => {
    const [inventario, setInventario] = useState([]);

    useEffect(() => {
        fetchInventario();
    }, []);

    const fetchInventario = () => {
        inventarioService.getInventario()
            .then(response => {
                setInventario(response.data);
                console.log('Inventario cargado:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar el inventario:', error);
            });
    };

    return (
        <div className="mx-4">
            <div className="mt-4">
                <h1 className="text-center">Inventario</h1>
                <table className="table table-bordered table-striped">
                    <thead className="bg-primary text-white thead-dark">
                        <tr>
                            <th className="text-center">Categoría</th>
                            <th className="text-center">Nombre del Insumo</th>
                            <th className="text-center">Unidad</th>
                            <th className="text-center">Cantidad Disponible</th>
                        </tr>
                    </thead>
                    <tbody>
                        {inventario.length > 0 ? (
                            inventario.map(item => (
                                <tr key={item.id}>
                                    <td>{item.insumo.categoria.nombreCategoria}</td>
                                    <td>{item.insumo.nombreInsumo}</td>
                                    <td>{item.insumo.unidad.nombreUnidad}</td>
                                    <td>{item.cantidadDisponible}</td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan="4" className="text-center">
                                    No hay inventario disponible.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListInventarioComponent;
