import React, { useState, useEffect } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import categoriaServiceInstance from '../services/CategoriaService';

const FormCategoriaComponent = () => {
    const [categoria, setCategoria] = useState({
        tipoCategoria: '',
        nombreCategoria: '',
    });

    const [tiposCategoria] = useState(['Insumo', 'Producto']); 
    const navigate = useNavigate();
    const { id } = useParams();

    useEffect(() => {
        if (id) {
            categoriaServiceInstance.getCategoriaById(id)
                .then(response => {
                    setCategoria(response.data);
                })
                .catch(error => console.error('Error al cargar la categoría:', error));
        }
    }, [id]);

    const handleInputChange = (e) => {
        const { name, value } = e.target;
        setCategoria({ ...categoria, [name]: value });
    };

    const handleSubmit = (e) => {
        e.preventDefault();

        if (id) {
            categoriaServiceInstance.editCategoria(id, categoria)
                .then(() => navigate('/categorias'))
                .catch(error => console.error('Error al actualizar la categoría:', error));
        } else {
            categoriaServiceInstance.saveCategoria(categoria)
                .then(() => navigate('/categorias'))
                .catch(error => console.error('Error al registrar la categoría:', error));
        }
    };

    const title = id ? 'Editar Categoría' : 'Registrar Categoría';

    return (
        <div className='mt-5 mb-4'>
            <div className='container'>
                <div className='row'>
                    <div className='card col-md-6 offset-md-3'>
                        <br />
                        <h2 className='text-center'>{title}</h2>
                        <div className='card-body'>
                            <form onSubmit={handleSubmit}>
                                {renderSelectField(
                                    'Tipo de Categoría',
                                    'tipoCategoria',
                                    categoria.tipoCategoria,
                                    tiposCategoria,
                                    handleInputChange
                                )}
                                {renderInputField(
                                    'Nombre de Categoría',
                                    'nombreCategoria',
                                    categoria.nombreCategoria,
                                    handleInputChange
                                )}
                                <div className='text-right'>
                                    <button type='submit' className='btn btn-success' style={{ marginRight: '20px' }}>
                                        Guardar
                                    </button>
                                    <button
                                        type='button'
                                        className='btn btn-danger'
                                        onClick={() => navigate('/categorias')}
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
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <input
            type='text'
            className='form-control'
            id={name}
            name={name}
            placeholder={label}
            value={value}
            onChange={onChange}
        />
    </div>
);

const renderSelectField = (label, name, value, options, onChange) => (
    <div className='form-group mb-3'>
        <label htmlFor={name}>{label}</label>
        <select
            className='form-control'
            id={name}
            name={name}
            value={value}
            onChange={onChange}
        >
            <option value="">Seleccione {label}</option>
            {options.map(option => (
                <option key={option} value={option}>
                    {option}
                </option>
            ))}
        </select>
    </div>
);

export default FormCategoriaComponent;
