import React, { useEffect, useState } from 'react';
import unidadService from '../services/UnidadService';
import { Link } from 'react-router-dom';

const ListUnidadesComponents = () => {
    const [unidades, setUnidades] = useState([]);

    useEffect(() => {
        fetchUnidades();
    }, []);

    const fetchUnidades = () => {
        unidadService.getAllUnidades()
            .then(response => {
                setUnidades(response.data);
                console.log('Unidades cargadas:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar las unidades:', error);
            });
    };

    const handleDelete = (idUnidad) => {
        if (window.confirm('¿Está seguro de que desea eliminar esta unidad?')) {
            unidadService.deleteUnidad(idUnidad)
                .then(() => {
                    console.log('Unidad eliminada:', idUnidad);
                    fetchUnidades();
                })
                .catch(error => {
                    console.error('Error al eliminar la unidad:', error);
                });
        }
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Unidades</h1>
                <Link to='/unidad/registro' className='btn btn-primary mb-3'>
                    Registrar Unidad
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Nombre de Unidad</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {unidades.length > 0 ? (
                            unidades.map(unidad => (
                                <tr key={unidad.idUnidad}>
                                    <td>{unidad.nombreUnidad}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/unidad/editar/${unidad.idUnidad}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(unidad.idUnidad)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan='2' className='text-center'>
                                    No hay unidades disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListUnidadesComponents;
