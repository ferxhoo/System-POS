import React, { useEffect, useState } from 'react';
import ClienteService from '../services/ClienteService';
import { Link } from 'react-router-dom';

const ListClientesComponents = () => {
    const [clientes, setClientes] = useState([]);

    useEffect(() => {
        fetchClientes();
    }, []);

    const fetchClientes = () => {
        ClienteService.getAllClientes()
            .then(response => {
                setClientes(response.data);
                console.log('Clientes cargados:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar los clientes:', error);
            });
    };

    const handleDelete = (idCliente) => {
        if (window.confirm('¿Está seguro de que desea eliminar este cliente?')) {
            ClienteService.deleteCliente(idCliente)
                .then(() => {
                    console.log('Cliente eliminado:', idCliente);
                    fetchClientes(); 
                })
                .catch(error => {
                    console.error('Error al eliminar el cliente:', error);
                });
        }
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Clientes</h1>
                <Link to='/cliente/registro' className='btn btn-primary mb-3'>
                    Registrar Cliente
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Tipo de Documento</th>
                            <th className='text-center'>Numero de Documento</th>
                            <th className='text-center'>Nombre Completo</th>
                            <th className='text-center'>Primer Apellido</th>
                            <th className='text-center'>Email</th>
                            <th className='text-center'>Teléfono</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {clientes.length > 0 ? (
                            clientes.map(cliente => (
                                <tr key={cliente.idCliente}>
                                    <td>{cliente.tipoDocumento}</td>
                                    <td>{cliente.numeroDocumento}</td>
                                    <td>{cliente.nombreCompleto}</td>
                                    <td>{cliente.primerApellido}</td>
                                    <td>{cliente.email}</td>
                                    <td>{cliente.telefono}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/cliente/editar/${cliente.idCliente}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(cliente.idCliente)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))) : (
                            <tr>
                                <td colSpan='7' className='text-center'>
                                    No hay clientes disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListClientesComponents;
