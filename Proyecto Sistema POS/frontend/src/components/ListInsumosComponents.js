import React, { useEffect, useState } from 'react';
import insumoService from '../services/InsumoService';
import { Link } from 'react-router-dom';

const ListInsumosComponents = () => {
    const [insumos, setInsumos] = useState([]);

    useEffect(() => {
        fetchInsumos();
    }, []);

    const fetchInsumos = () => {
        insumoService.getAllInsumos()
            .then(response => {
                setInsumos(response.data);
                console.log('Insumos cargados:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar los insumos:', error);
            });
    };

    const handleDelete = (idInsumo) => {
        if (window.confirm('¿Está seguro de que desea eliminar este insumo?')) {
            insumoService.deleteInsumo(idInsumo)
                .then(() => {
                    console.log('Insumo eliminado:', idInsumo);
                    fetchInsumos(); 
                })
                .catch(error => {
                    console.error('Error al eliminar el insumo:', error);
                });
        }
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Insumos</h1>
                <Link to='/insumo/registro' className='btn btn-primary mb-3'>
                    Registrar Insumo
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Categoría</th>
                            <th className='text-center'>Nombre</th>
                            <th className='text-center'>Unidad</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {insumos.length > 0 ? (
                            insumos.map(insumo => (
                                <tr key={insumo.idInsumo}>
                                    <td>{insumo.categoria?.nombreCategoria || 'Sin categoría'}</td>
                                    <td>{insumo.nombreInsumo}</td>
                                    <td>{insumo.unidad?.nombreUnidad || 'Sin unidad'}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/insumo/editar/${insumo.idInsumo}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(insumo.idInsumo)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan='5' className='text-center'>
                                    No hay insumos disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListInsumosComponents;
