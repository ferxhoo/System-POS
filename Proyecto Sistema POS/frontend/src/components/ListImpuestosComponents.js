import React, { useEffect, useState } from 'react';
import ImpuestoService from '../services/ImpuestoService';
import { Link } from 'react-router-dom';

const ListImpuestosComponents = () => {
    const [impuestos, setImpuestos] = useState([]);

    useEffect(() => {
        fetchImpuestos();
    }, []);

    const fetchImpuestos = () => {
        ImpuestoService.getAllImpuestos()
            .then(response => {
                setImpuestos(response.data);
                console.log('Impuestos cargados:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar los impuestos:', error);
            });
    };

    const handleDelete = (idImpuesto) => {
        if (window.confirm('¿Está seguro de que desea eliminar este impuesto?')) {
            ImpuestoService.deleteImpuesto(idImpuesto)
                .then(() => {
                    console.log('Impuesto eliminado:', idImpuesto);
                    fetchImpuestos();
                })
                .catch(error => {
                    console.error('Error al eliminar el impuesto:', error);
                });
        }
    };

    const formatTarifa = (tarifa) => {
        const tarifaPercent = parseFloat(tarifa) * 100;
        return tarifaPercent % 1 === 0 ? tarifaPercent.toFixed(0) + '%' : tarifaPercent.toFixed(2) + '%';
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Impuestos</h1>
                <Link to='/impuesto/registro' className='btn btn-primary mb-3'>
                    Registrar Impuesto
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Nombre del Impuesto</th>
                            <th className='text-center'>Concepto</th>
                            <th className='text-center'>Tarifa</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {impuestos.length > 0 ? (
                            impuestos.map(impuesto => (
                                <tr key={impuesto.idImpuesto}>
                                    <td>{impuesto.nombreImpuesto}</td>
                                    <td>{impuesto.concepto}</td>
                                    <td>{formatTarifa(impuesto.tarifa)}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/impuesto/editar/${impuesto.idImpuesto}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(impuesto.idImpuesto)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan='4' className='text-center'>
                                    No hay impuestos disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListImpuestosComponents;
