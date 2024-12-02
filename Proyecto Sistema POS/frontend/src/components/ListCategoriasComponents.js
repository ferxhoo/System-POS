import React, { useEffect, useState } from 'react';
import categoriaService from '../services/CategoriaService';
import { Link } from 'react-router-dom';

const ListCategoriasComponents = () => {
    const [categorias, setCategorias] = useState([]);

    useEffect(() => {
        fetchCategorias();
    }, []);

    const fetchCategorias = () => {
        categoriaService.getAllCategorias()
            .then(response => {
                setCategorias(response.data);
                console.log('Categorías cargadas:', response.data);
            })
            .catch(error => {
                console.error('Error al cargar las categorías:', error);
            });
    };

    const handleDelete = (idCategoria) => {
        if (window.confirm('¿Está seguro de que desea eliminar esta categoría?')) {
            categoriaService.deleteCategoria(idCategoria)
                .then(() => {
                    console.log('Categoría eliminada:', idCategoria);
                    fetchCategorias();
                })
                .catch(error => {
                    console.error('Error al eliminar la categoría:', error);
                });
        }
    };

    return (
        <div className='mx-4'>
            <div className='mt-4'>
                <h1 className='text-center'>Lista de Categorías</h1>
                <Link to='/categoria/registro' className='btn btn-primary mb-3'>
                    Registrar Categoría
                </Link>
                <table className='table table-bordered table-striped'>
                    <thead className='bg-primary text-white thead-dark'>
                        <tr>
                            <th className='text-center'>Tipo de Categoría</th>
                            <th className='text-center'>Nombre de Categoría</th>
                            <th className='text-center'>Acciones</th>
                        </tr>
                    </thead>
                    <tbody>
                        {categorias.length > 0 ? (
                            categorias.map(categoria => (
                                <tr key={categoria.idCategoria}>
                                    <td>{categoria.tipoCategoria}</td>
                                    <td>{categoria.nombreCategoria}</td>
                                    <td>
                                        <div className='d-flex justify-content-center'>
                                            <Link to={`/categoria/editar/${categoria.idCategoria}`} className='btn btn-primary mr-3'>
                                                Editar
                                            </Link>
                                            <button className='btn btn-danger ml-3' onClick={() => handleDelete(categoria.idCategoria)}>
                                                Eliminar
                                            </button>
                                        </div>
                                    </td>
                                </tr>
                            ))
                        ) : (
                            <tr>
                                <td colSpan='3' className='text-center'>
                                    No hay categorías disponibles.
                                </td>
                            </tr>
                        )}
                    </tbody>
                </table>
            </div>
        </div>
    );
};

export default ListCategoriasComponents;
