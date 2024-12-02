import React, { useEffect, useState } from 'react';
import ProveedorService from '../services/ProveedorService';
import { Link } from 'react-router-dom';

const ListProveedoresComponents = () => {
    const [proveedores, setProveedores] = useState([]);

    useEffect(() => {
        fetchProveedores();
    }, []);

    const fetchProveedores = () => {
        ProveedorService.getAllProveedores()
            .then(response => {
                setProveedores(response.data);
                console.log('Proveedores cargados:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar los proveedores:', error);
            });
    };

    const handleDelete = (idProveedor) => {
        if (window.confirm('¿Está seguro de que desea eliminar este proveedor?')) {
            ProveedorService.deleteProveedor(idProveedor)
                .then(() => {
                    console.log('Proveedor eliminado:', idProveedor);
                    fetchProveedores();
                })
                .catch(error => {
                    console.error('Error al eliminar el proveedor:', error);
                });
        }
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Proveedores</h1>
                <Link to='/proveedor/registro' className='btn btn-primary mb-3'>
                    Registrar Proveedor
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Tipo de Proveedor</th>
                            <th className='text-center'>Tipo de Documento</th>
                            <th className='text-center'>Número de Documento</th>
                            <th className='text-center'>Nombre del Proveedor</th>
                            <th className='text-center'>Dirección</th>
                            <th className='text-center'>Teléfono</th>
                            <th className='text-center'>Email</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {proveedores.length > 0 ? (
                            proveedores.map(proveedor => (
                                <tr key={proveedor.idProveedor}>
                                    <td>{proveedor.tipoProveedor}</td>
                                    <td>{proveedor.tipoDocumento}</td>
                                    <td>{proveedor.numeroDocumento}</td>
                                    <td>{proveedor.nombreProveedor}</td>
                                    <td>{proveedor.direccion}</td>
                                    <td>{proveedor.telefono}</td>
                                    <td>{proveedor.email}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/proveedor/editar/${proveedor.idProveedor}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(proveedor.idProveedor)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan='5' className='text-center'>
                                    No hay proveedores disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListProveedoresComponents;
